package com.hw9;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CacheHandler implements InvocationHandler {

    private Object delegate;
    private CacheMemory cacheMemory;

    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        this.cacheMemory = new CacheMemory();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        cacheMemory.addRow((String) args[0], (Integer) args[1]);
        FileOutputStream fos = new FileOutputStream("cache.bin");
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(cacheMemory);
        fos.close();
        out.close();
        return method.invoke(delegate, args);
    }
}
