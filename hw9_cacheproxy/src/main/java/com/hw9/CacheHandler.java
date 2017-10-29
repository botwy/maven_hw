package com.hw9;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//обработчик метода для прокси
public class CacheHandler implements InvocationHandler {

    private final Object delegate;
    private final MemoryCache memoryCache;
    private final String root_folder;

   // private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   // private final Lock r = readWriteLock.readLock();
   // private final Lock w = readWriteLock.writeLock();

    private IMode modeCache=new DefaultModeImpl();

    public Object getDelegate() {
        return delegate;
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public String getRoot_folder() {
        return root_folder;
    }

    /**
    * @param delegate в конструктор передаем Объект, реализующий интерфейс (кешируемый сервис), за которым будем следить,
    * @param root_folder и рутовую папку, где хранится файл, в который сериализуется кеш, в случаи режима кеширования FILE
    */
    public CacheHandler(Object delegate, String root_folder) {
        this.delegate = delegate;
        this.root_folder = root_folder;
        this.memoryCache = new MemoryCache();
    }

    /**
     *Сначала проверяем есть ли аннотация @CacheAnnot на методе нашего сервиса
     * Если нет, выполняем метод сервиса без кеширования
     * Если есть, используем кеширование
     * Используя modeCache запрашиваем и получаем текущее состояние IMode (режим в котором нужно делать кеширование)
     * modeCache.exec выполняет кеширование согласно текущего состояния
     * @param proxy
     * @param method
     * @param args
     * @return результат выполнения метода сервиса
     * @throws InvocationTargetException в случае, если произойдет исключение внутри invoke метода  объекта-делегата нашего сервиса
     * @throws IllegalAccessException в случае при исполнении invoke метода объекта-делегата нашего сервиса,
     * этот метод будет не доступен (not access)
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        if (method.isAnnotationPresent(CacheAnnot.class)) {
            CacheAnnot an = method.getAnnotation(CacheAnnot.class);
            modeCache = modeCache.askCurrMode(an);
            return modeCache.exec(this,method,args,an);
        }


        return method.invoke(delegate, args);
    }



}