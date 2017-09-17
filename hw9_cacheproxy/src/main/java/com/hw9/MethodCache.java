package com.hw9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodCache implements Serializable{
    private List<List<Object>> list_list_object;
    private static final long serialVersionUID = 1L;

    public MethodCache(){
        this.list_list_object = new ArrayList<>();
    }

    public void addRow(List<Object> list_obj) {
        list_list_object.add(list_obj);
    }

    public List<List<Object>> getList_object() {
        return list_list_object;
    }

    public void setList_list_object(List<List<Object>> list_list_object) {
        this.list_list_object = list_list_object;
    }
}
