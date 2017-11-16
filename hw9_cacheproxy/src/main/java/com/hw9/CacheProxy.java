package com.hw9;


import java.lang.reflect.Proxy;

/**
 * создаёт кеширующий прокси для интерфейса любого типа
 */
public class CacheProxy {
private String root_folder="";

    public CacheProxy() {
    }

    /**
     *
     * @param root_folder рут-папка для хранения файлов с кешируемыми данными при режиме кеширования в файл
     */
    public CacheProxy(String root_folder) {
        this.root_folder = root_folder;
    }

    /**
     * включение кеширования
     * @param service объект-делегат, реализующий интерфейс(сервис) со сложными вычислениями
     * @param <T>
     * @return
     */
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
