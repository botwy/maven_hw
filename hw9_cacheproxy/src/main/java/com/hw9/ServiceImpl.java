package com.hw9;

import com.hw9.FileMemory;

import static com.hw9.FileMemory.FILE;
import static com.hw9.FileMemory.IN_MEMORY;


public class ServiceImpl implements IService{

    @Cache(cacheType = FILE,identityBy = {String.class, Integer.class})
    @Override
    public double doHardWork(String action, Integer arg2) {
return arg2*1.123f;
    }

}
