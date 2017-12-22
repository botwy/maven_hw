package com.hw9;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * используется для хранения составного ключа и результата вычисления
 * один объект на каждый метод
 * в режиме FILE сериализуется в файл
 * конкуррентный доступ к хранилищу ConcurrentHashMap<ElementKey,Double>
 */
public class DataObject implements Serializable{
    private static final long serialVersionUID = 1L;
    private ConcurrentHashMap<ElementKey,Double> map=new ConcurrentHashMap<>();

    public void put (ElementKey elementKey, double result) {
        map.put(elementKey,result);
    }

    public Double findResult(ElementKey elementKey) {
        if (map.containsKey(elementKey))
            return map.get(elementKey);

        return null;
    }
}
