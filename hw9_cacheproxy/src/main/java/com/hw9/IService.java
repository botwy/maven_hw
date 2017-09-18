package com.hw9;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.hw9.FileMemory.FILE;
import static com.hw9.FileMemory.IN_MEMORY;


public interface IService {

    @Cache(cacheType = FILE,identityBy = {String.class, Integer.class})
    double doHardWork(String action, Integer arg2);
}
