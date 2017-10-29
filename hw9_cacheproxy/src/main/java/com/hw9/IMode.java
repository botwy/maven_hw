package com.hw9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * State или режим кеширования
 */
public interface IMode {

    /**
     * Выполнение кеширования согласно конкретной реализации режима (состояния)
     * @param context ссылка на объект нашего CacheHandler
     * @param method
     * @param args
     * @param an
     * @return
     * @throws InvocationTargetException в случае, если произойдет исключение внутри invoke метода  объекта-делегата нашего сервиса
     * @throws IllegalAccessException в случае при исполнении invoke метода объекта-делегата нашего сервиса,
     * этот метод будет не доступен (not access)
     */
    Object exec (CacheHandler context, Method method, Object[] args, CacheAnnot an)
            throws InvocationTargetException, IllegalAccessException;

    /**
     *
     * @param an
     * @return Возвращает на основе аннотации текущее состояние IMode (режим в котором нужно делать кеширование)
     */
    IMode askCurrMode(CacheAnnot an);

}
