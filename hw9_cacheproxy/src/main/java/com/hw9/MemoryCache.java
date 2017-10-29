package com.hw9;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Для кеширование в память. Хранилище имен кешируемых методов
 * (или fileNamePrefix аннотации @CacheAnnot) и объектов {@link DataObject} c результатами
 * конкуррентный доступ к хранилищу ConcurrentHashMap<String,DataObject>
 */
public class MemoryCache {
    private final ConcurrentHashMap<String,DataObject> data;

    public MemoryCache(){
        this.data = new ConcurrentHashMap<String, DataObject>();
    }

    public boolean addCacheOfMethod(String method_name, DataObject fCache) {
        data.put(method_name,fCache);
        return true;
    }

    public ConcurrentHashMap<String, DataObject> getData() {
        return data;
    }

}
