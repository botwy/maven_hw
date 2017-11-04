package com.hw9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Если режим IN_MEMORY, то кеширование пишем в оперативную память
 * объекты DataObject хранятся в объекте MemoryCache в соответствии с именами кешируемых методов
 * (или в соответсвии с fileNamePrefix аннотации @CacheAnnot)
 */
public class MemoryModeImpl extends AbstractMode {

    @Override
    public Object exec (CacheHandler context, Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        CacheAnnot an = method.getAnnotation(CacheAnnot.class);
        String prefix = askPrefix(an, method);
        MemoryCache memoryCache = context.getMemoryCache();
        Double result = null;
        DataObject dataObject = null;
        ElementKey multiple_key = new ElementKey(prefix, getKeysFromArgs(an, args));

        if (memoryCache.getData().containsKey(prefix)) {
            dataObject = memoryCache.getData().get(prefix);

            result = dataObject.findResult(multiple_key);
            if (result != null)
                return result;
        }


        result = (Double) method.invoke(context.getDelegate(), args);

        if (dataObject == null)
            dataObject = new DataObject();

        dataObject.put(multiple_key, result);

        memoryCache.addCacheOfMethod(prefix, dataObject);


        return result;
    }
}
