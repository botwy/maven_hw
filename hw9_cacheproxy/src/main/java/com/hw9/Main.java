package com.hw9;

import java.lang.reflect.Proxy;

@Deprecated
public class Main {

    public static void main(String[] args) {
     /*  IService service = (IService) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{IService.class},
                new CacheHandler(new ServiceImpl())
        );*/
        IService service = new ServiceImpl();
        CacheProxy cacheProxy = new CacheProxy("cache_files");
        IService serviceCache = cacheProxy.cache(service);



        double r1=0;

        r1 = serviceCache.doHardWork("work1",3);
        System.out.println(r1);
        r1 = serviceCache.doHardWork("work1",2);
        System.out.println(r1);

    }
}
