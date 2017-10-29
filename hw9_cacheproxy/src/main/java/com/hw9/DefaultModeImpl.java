package com.hw9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Если согласно аннотации не найден соответствующий режим
 * выполняем дефолтный режим. Исполняем метод делегата без кеширования
 */
public class DefaultModeImpl extends AbstractMode {

    @Override
    public Object exec (CacheHandler context, Method method, Object[] args, CacheAnnot an)
            throws InvocationTargetException, IllegalAccessException {
        return method.invoke(context.getDelegate(), args);
    }
}
