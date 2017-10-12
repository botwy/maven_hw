package com.hw9;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheHandler implements InvocationHandler {

    private final Object delegate;
    private final CacheMemory cacheMemory;
    private final String root_folder;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock r = readWriteLock.readLock();
    private final Lock w = readWriteLock.writeLock();

    public CacheHandler(Object delegate, String root_folder) {
        this.delegate = delegate;
        this.root_folder = root_folder;
        this.cacheMemory = new CacheMemory();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        if (method.isAnnotationPresent(Cache.class)) {
            Cache an = method.getAnnotation(Cache.class);
            String prefix = "";

            if (an.fileNamePrefix().equals(""))
                prefix = method.getName();
            else
                prefix = an.fileNamePrefix();

            if (an.cacheType() == FileMemory.FILE) {

                String file_name = "";
                if (root_folder.length() > 0) {
                    File folder = new File(root_folder);
                    if (!folder.exists())
                        folder.mkdir();

                    file_name = root_folder + "/" + prefix;
                } else file_name = prefix;

                File file = new File(file_name);

                FileCache fileCache = null;
                if (file.exists())
                    fileCache = readFcFromFile(file_name);

                if (fileCache == null)
                    fileCache = new FileCache();

                ElementKey multiple_key = new ElementKey(prefix, getKeysFromArgs(an, args));
                Double result = fileCache.findResult(multiple_key);

                if (result != null)
                    return result;

                result = (Double) method.invoke(delegate, args);

                fileCache.put(multiple_key, result);
                writeFcToFile(file_name, fileCache);

                return result;
            } else if (an.cacheType() == FileMemory.IN_MEMORY) {
                Double result = null;
                FileCache fileCache = null;
                ElementKey multiple_key = new ElementKey(prefix, getKeysFromArgs(an, args));

                r.lock();
                try {
                    if (cacheMemory.getData().containsKey(prefix)) {
                        fileCache = cacheMemory.getData().get(prefix);

                        result = fileCache.findResult(multiple_key);
                        if (result != null)
                            return result;
                    }
                } finally {
                    r.unlock();
                }

                result = (Double) method.invoke(delegate, args);

                if (fileCache == null)
                    fileCache = new FileCache();

                fileCache.put(multiple_key, result);

                w.lock();
                try {
                    cacheMemory.addCacheOfMethod(prefix, fileCache);
                } finally {
                    w.unlock();
                }

                return result;
            }

        }
        return method.invoke(delegate, args);
    }


    boolean checkContains(Class cl, Class[] arr_cl) {
        for (Class item : arr_cl
                ) {
            if (item.equals(cl))
                return true;
        }
        return false;
    }


    FileCache readFcFromFile(String file_name) {
        FileCache fileCache = null;

        try (FileInputStream fis = new FileInputStream(file_name);
             ObjectInputStream in = new ObjectInputStream(fis)
        ) {
            fileCache = (FileCache) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return fileCache;
    }


    List<Object> getKeysFromArgs(Cache an, Object[] args) {
        List<Object> result = new ArrayList<>();
        if (an.identityBy().length == 0) return Arrays.asList(args);

        for (int i = 0; i < args.length; i++) {

            if (checkContains(args[i].getClass(), an.identityBy()))
                result.add(args[i]);


        }
        return result;
    }


    // Сериализация. Запись в файл.
    void writeFcToFile(String file_name, FileCache fileCache) {
        try (FileOutputStream fos = new FileOutputStream(file_name);
             ObjectOutputStream out = new ObjectOutputStream(fos);
        ) {
            out.writeObject(fileCache);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}