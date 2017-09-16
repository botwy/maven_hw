package com.hw9;

public class ServiceImpl implements IService{

    @Cache
    @Override
    public double doHardWork(String action, Integer arg2) {
return arg2*1.123f;
    }

}
