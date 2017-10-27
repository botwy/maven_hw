package com.hw9;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Для кеширование в память. Хранилище имен кешируемых методов
 * (или fileNamePrefix аннотации @Cache) и объектов {@link FileCache} c результатами
 */
public class CacheMemory{
    private final HashMap<String,FileCache> data;

    public CacheMemory(){
        this.data = new HashMap<String, FileCache>();
    }

    public boolean addCacheOfMethod(String method_name, FileCache fCache) {
        data.put(method_name,fCache);
        return true;
    }

    public HashMap<String, FileCache> getData() {
        return data;
    }

}
