package com.hw9;

import static com.hw9.FileMemoryEnum.FILE;
import static com.hw9.FileMemoryEnum.IN_MEMORY;


public interface IService {

    /**
     * метод сложного вычисления
     * @param action
     * @param arg2
     * @return double результат вычисления
     */
    @CacheAnnot(cacheType = FILE,fileNamePrefix = "IService_doHardWork", identityBy = {String.class,Integer.class})
    double doHardWork(String action, Integer arg2);
}
