package com.hw9;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Если режим FILE, то кеширование осуществляем через сериализацию в/из файла на диске
 * объект DataObject читается из файла с именем, соответствующем имени кещируемого метода (или fileNamePrefix аннотации @CacheAnnot
 * кешируемого метода)
 */
public class FileModeImpl extends AbstractMode{
    @Override
    public Object exec (CacheHandler context, Method method, Object[] args, CacheAnnot an)
            throws InvocationTargetException, IllegalAccessException {

        String prefix = askPrefix(an, method);
        String file_name = prefix;
        if (context.getRoot_folder().length() > 0) {
            File folder = new File(context.getRoot_folder());
            if (!folder.exists())
                folder.mkdir();

            file_name = context.getRoot_folder() + "/" + prefix;
        }

        File file = new File(file_name);

        DataObject dataObject = null;
        if (file.exists())
            dataObject = readFcFromFile(file_name);

        if (dataObject == null)
            dataObject = new DataObject();

        ElementKey multiple_key = new ElementKey(prefix, getKeysFromArgs(an, args));
        Double result = dataObject.findResult(multiple_key);

        if (result != null)
            return result;

        result = (Double) method.invoke(context.getDelegate(), args);

        dataObject.put(multiple_key, result);
        writeFcToFile(file_name, dataObject);

        return result;
    }
}

