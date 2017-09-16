package com.hw9;

import java.io.Serializable;
import java.util.HashMap;

public class CacheMemory implements Serializable{
    private HashMap<String,Integer> data;
    private static final long serialVersionUID = 1L;
    public CacheMemory(){
        this.data = new HashMap<String, Integer>();
    }

    public boolean addRow(String action, Integer arg2) {
        data.put(action,arg2);
        return true;
    }
}
