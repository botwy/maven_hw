package com.hw9.modes;

import com.hw9.CacheHandler;
import com.hw9.exception.DbCacheException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * State или режим кеширования
 */
public interface IMode {

    /**
     * Выполнение кеширования согласно конкретной реализации режима (состояния)
     * @param context ссылка на объект нашего CacheHandler
     * @param method
     * @param args
     * @return
     * @throws InvocationTargetException в случае, если произойдет исключение внутри invoke метода  объекта-делегата нашего сервиса
     * @throws IllegalAccessException в случае при исполнении invoke метода объекта-делегата нашего сервиса,
     * этот метод будет не доступен (not access)
     */
    Object exec (CacheHandler context, Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException, SQLException, DbCacheException;

    /**
     *
     * @return Возвращает на основе аннотации текущее состояние IMode (режим в котором нужно делать кеширование)
     */
    IMode askCurrMode(Method method);

}
