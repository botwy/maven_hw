package com.hw9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Составной ключ для использования в кешировании для поиска результата
public class ElementKey implements Serializable{
    private final List<Object> multiple_key;
    private static final long serialVersionUID = 1L;

    public ElementKey(String method, List<Object> keys) {
        this.multiple_key = new ArrayList<Object>();
        multiple_key.add(method);
        for (int i = 0; i <keys.size() ; i++) {
            multiple_key.add(keys.get(i));
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
