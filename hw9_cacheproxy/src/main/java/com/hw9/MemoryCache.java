package com.hw9;

import java.util.HashMap;

/**
 * Для кеширование в память. Хранилище имен кешируемых методов
 * (или fileNamePrefix аннотации @CacheAnnot) и объектов {@link DataObject} c результатами
 */
public class MemoryCache {
    private final HashMap<String,DataObject> data;

    public MemoryCache(){
        this.data = new HashMap<String, DataObject>();
    }

    public boolean addCacheOfMethod(String method_name, DataObject fCache) {
        data.put(method_name,fCache);
        return true;
    }

    public HashMap<String, DataObject> getData() {
        return data;
    }

}
