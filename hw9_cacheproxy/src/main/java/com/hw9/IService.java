package com.hw9;

import com.hw9.annotation.Cachable;
import com.hw9.modes.AbstractMode;


public interface IService {

    /**
     * метод сложного вычисления
     * @param action
     * @param arg2
     * @return double результат вычисления
     */
  //  @CacheAnnot(cacheType = FILE,fileNamePrefix = "IService_doHardWork", identityBy = {String.class,Integer.class})
    @Cachable(persistent = true)
    double doHardWork(String action, Integer arg2);
}
