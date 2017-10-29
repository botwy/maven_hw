package com.hw9;


import static com.hw9.FileMemoryEnum.FILE;

// реализация сервиса со сложным вычислением
public class ServiceImpl implements IService{


    public double doHardWork(String action, Integer arg2) {
return arg2*1.123f;
    }

}
