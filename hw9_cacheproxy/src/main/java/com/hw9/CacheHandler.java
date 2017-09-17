package com.hw9;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CacheHandler implements InvocationHandler {

    private Object delegate;
    private CacheMemory cacheMemory;


    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        this.cacheMemory = new CacheMemory();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache an = method.getAnnotation(Cache.class);
            String prefix = "";

            if (an.fileNamePrefix().equals(""))
                prefix = method.getName();
            else
                prefix = an.fileNamePrefix();

            if (an.cacheType() == FileMemory.FILE) {

                String file_name = prefix;

                File file = new File(file_name);
                MethodCache mc = null;
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file_name);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    mc = (MethodCache) in.readObject();
                    fis.close();
                    in.close();
                }
                if (mc == null)
                    mc = new MethodCache();

                Object result = findMatchObject(mc,an,args);
                if(result!=null)
                    return result;
                List<Object> new_list_obj = new ArrayList<>();
                for (Object o : args
                        ) {
                    if (checkContains(o.getClass(),an.identityBy()))
                    new_list_obj.add(o);
                }
                result = method.invoke(delegate, args);
                new_list_obj.add(result);
                mc.addRow(new_list_obj);
                //  mc.setList_list_object(list_list_obj);
                FileOutputStream fos = new FileOutputStream(file_name);
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(mc);
                fos.close();
                out.close();

                return result;
            }else
            if (an.cacheType() == FileMemory.IN_MEMORY) {
                Object result = null;
                MethodCache mc = null;
                if(cacheMemory.getData().containsKey(prefix)) {
                    mc = cacheMemory.getData().get(prefix);
                  result = findMatchObject(mc,an,args);
                    if(result!=null)
                        return result;

                }
                List<Object> new_list_obj = new ArrayList<>();
                for (Object o : args
                        ) {
                    if (checkContains(o.getClass(),an.identityBy()))
                    new_list_obj.add(o);
                }
                result = method.invoke(delegate, args);
                new_list_obj.add(result);
                if (mc==null)
                    mc=new MethodCache();
                mc.addRow(new_list_obj);
                cacheMemory.addRow(prefix,mc);
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

    Object getCacheArgByClassName(Class cl, List<Object> list_obj) {
        for (Object obj : list_obj
                ) {
            if (obj.getClass().equals(cl))
                return obj;
        }
        return null;
    }

    Object findMatchObject(MethodCache mc, Cache an, Object[] args) {
        List<List<Object>> list_list_obj = mc.getList_object();
        for (List<Object> list_obj : list_list_obj
                ) {
            boolean match_result = true;
            for (int i = 0; i < args.length; i++) {
                if (an.identityBy().length == 0) {
                    if (checkContains(args[i].getClass(), an.identityBy())) {
                        Object o = getCacheArgByClassName(args[i].getClass(), list_obj);
                        if (o == null || !o.equals(args[i]))
                            match_result = false;
                    }
                } else if (!args[i].equals(list_obj.get(i)))
                    match_result = false;

            }
            if (match_result)
                return list_obj.get(list_obj.size() - 1);

        }
        return null;
    }
}
