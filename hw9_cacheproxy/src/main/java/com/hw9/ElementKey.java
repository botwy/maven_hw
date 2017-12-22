package com.hw9;

import sun.rmi.transport.ObjectTable;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * ElementKey - immutable
 * Составной ключ для использования в кешировании для поиска результата
 */
public class ElementKey implements Serializable{
    private final List<Object> multiple_key;
    private static final long serialVersionUID = 1L;

    /**
     * создание составного ключа с параметрами:
     * Boolean, Byte, Character, Double, Float, Integer, Long, Short, String - immutable
     * если один из аргументов Date, то используем копию
     * @param method имя метода или префикс, включается в состаной ключ
     * @param keys список учитываемых для уникальности аргументов
     */
    public ElementKey(String method, List<Object> keys) {
        this.multiple_key = new ArrayList<Object>();
        multiple_key.add(method);
        for (int i = 0; i <keys.size() ; i++) {
            Object curr_obj = keys.get(i);
            if (curr_obj.getClass().equals(Date.class))
               curr_obj = new Date(((Date)curr_obj).getTime());

            multiple_key.add(curr_obj);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementKey that = (ElementKey) o;

        return multiple_key.equals(that.multiple_key);
    }

    @Override
    public int hashCode() {
        return multiple_key.hashCode();
    }
}
