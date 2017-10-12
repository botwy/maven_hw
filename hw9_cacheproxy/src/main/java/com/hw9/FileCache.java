package com.hw9;

import java.io.Serializable;
import java.util.HashMap;

public class FileCache implements Serializable{
    private static final long serialVersionUID = 1L;
    private HashMap<ElementKey,Double> map=new HashMap<>();

    public void put (ElementKey elementKey, double result) {
        map.put(elementKey,result);
    }

    public Double findResult(ElementKey elementKey) {
        if (map.containsKey(elementKey))
            return map.get(elementKey);

        return null;
    }
}