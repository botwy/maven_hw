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

//обработчик метода для прокси
public class CacheHandler implements InvocationHandler {

    private final Object delegate;
    private final CacheMemory cacheMemory;
    private final String root_folder;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock r = readWriteLock.readLock();
    private final Lock w = readWriteLock.writeLock();

   /**
    * @param delegate в конструктор передаем Объект, реализующий интерфейс (кешируемый сервис), за которым будем следить,
    * @param root_folder и рутовую папку, где хранится файл, в который сериализуется кеш, в случаи режима кеширования FILE
    */
    public CacheHandler(Object delegate, String root_folder) {
        this.delegate = delegate;
        this.root_folder = root_folder;
        this.cacheMemory = new CacheMemory();
    }

    /**
     *Сначала проверяем есть ли аннотация @Cache на методе нашего сервиса
     * Если нет, выполняем метод сервиса без кеширования
     * Если есть, используем кеширование
     * Если режим FILE, то кеширование осуществляем через сериализацию в/из файла на диске
     * Если режим IN_MEMORY, то кеширование пишем в оперативную память
     * При кешировании в обоих режимах используем объект FileCache, содержащий составной ключ из аргументов, учитываемых
     * при определении уникальности результата и сам результат кешируемого метода
     * В режиме FILE этот объект FileCache читается из файла с именем, соответствующем имени кещируемого метода (или fileNamePrefix аннотации @Cache
     * кешируемого метода)
     * В режиме IN_MEMORY объекты FileCache хранятся в объекте CacheMemory в соответствии с именами кешируемых методов
     * (или в соответсвии с fileNamePrefix аннотации @Cache)
     * @param proxy
     * @param method
     * @param args
     * @return результат выполнения метода сервиса
     * @throws InvocationTargetException в случае, если произойдет исключение внутри invoke метода  объекта-делегата нашего сервиса
     * @throws IllegalAccessException в случае при исполнении invoke метода объекта-делегата нашего сервиса,
     * этот метод будет не доступен (not access)
     */
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

    /**
     *
     * @param cl
     * @param arr_cl
     * @return возвращает true, если тип cl входит в массив arr_cl
     */
    boolean checkContains(Class cl, Class[] arr_cl) {
        for (Class item : arr_cl
                ) {
            if (item.equals(cl))
                return true;
        }
        return false;
    }

    /**
     *
     * @param file_name в котором хранятся закешированные данные
     * @return читает из файла, десериализует и возвращает объект FileCache, хранящий
     * десериализованные данные кеша
     */
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


    /**
     *
     * @param an
     * @param args
     * @return возвращаем список только тех аргументов, которые учитываются при определении уникальности результата
     */
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