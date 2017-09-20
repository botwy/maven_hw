package com.hw9;


import java.lang.reflect.Proxy;

public class CacheProxy {
private String root_folder="";

    public CacheProxy() {
    }

    public CacheProxy(String root_folder) {
        this.root_folder = root_folder;
    }

    public <T> T cache(T service) {
        T cache_service=null;
        Class cl = service.getClass().getInterfaces()[0];
        cache_service = (T) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{service.getClass().getInterfaces()[0]},
                new CacheHandler(service,root_folder)
        );
        return cache_service;
    }

    public String getRoot_folder() {
        return root_folder;
    }
}
