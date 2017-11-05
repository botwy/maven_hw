package com.hw9;

import java.lang.reflect.Proxy;


public class Main {

    public static void main(String[] args) {

        IService service = new ServiceImpl();
        CacheProxy cacheProxy = new CacheProxy("cache_files");
        IService serviceCache = cacheProxy.cache(service);


        Object r1=0;

        r1 = serviceCache.doHardWork("work1",3);
        System.out.println(r1);
        r1 = serviceCache.doHardWork("work1",2);
        System.out.println(r1);
        r1 = serviceCache.doHardWork("work2",5);
        System.out.println(r1);

    }
}
