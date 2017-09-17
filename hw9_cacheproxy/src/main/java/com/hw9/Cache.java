package com.hw9;

public @interface Cache {

    FileMemory cacheType();

    String fileNamePrefix() default "";

    Class[] identityBy() default {};
}
