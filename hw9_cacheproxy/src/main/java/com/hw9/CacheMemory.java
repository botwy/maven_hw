package com.hw9;

import java.io.Serializable;
import java.util.HashMap;

public class CacheMemory implements Serializable{
    private HashMap<String,MethodCache> data;

    public CacheMemory(){
        this.data = new HashMap<String, MethodCache>();
    }

    public boolean addRow(String method_name, MethodCache methodCache) {
        data.put(method_name,methodCache);
        return true;
    }

    public HashMap<String, MethodCache> getData() {
        return data;
    }

    public void setData(HashMap<String, MethodCache> data) {
        this.data = data;
    }
}
