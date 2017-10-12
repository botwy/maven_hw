package com.hw9;

import java.io.Serializable;
import java.util.HashMap;

public class CacheMemory{
    private HashMap<String,FileCache> data;

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

    public void setData(HashMap<String, FileCache> data) {
        this.data = data;
    }
}
