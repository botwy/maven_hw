package com.hw9.modes;

import com.hw9.DataObject;
import com.hw9.FileMemoryEnum;
import com.hw9.annotation.Cachable;
import com.hw9.annotation.CacheAnnot;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Абстрактный режим кеширования, где есть общие методы для конкретных режимов
 * При кешировании в режимах FILE и IN_MEMORY используем объект DataObject, содержащий составной ключ
 * из названия метода, аргументов, учитываемых
 * при определении уникальности результата и сам результат кешируемого метода
 *
 * Если есть аннотация Cachable и её поле persistent true, то кеширование осуществляем через базу данных h2 (режим h2DBModeImpl)
 * Если persistent false, то кешируем в оперативной памяти (используем режим MemoryModeImpl)
 */
public abstract class AbstractMode implements IMode {

    @Override
    public IMode askCurrMode(Method method) {
        if (method.isAnnotationPresent(CacheAnnot.class)) {
            CacheAnnot an = method.getAnnotation(CacheAnnot.class);
            if (an.cacheType() == FileMemoryEnum.IN_MEMORY)
                return new MemoryModeImpl();

            if (an.cacheType() == FileMemoryEnum.FILE)
                return new FileModeImpl();

        }

        if (method.isAnnotationPresent(Cachable.class)) {
            Cachable an = method.getAnnotation(Cachable.class);
            if (an.persistent())
                return new h2DBModeImpl();

            if (!an.persistent())
                return new MemoryModeImpl();
        }
        return new DefaultModeImpl();
    }

    /**
     * @param an
     * @param method
     * @return Получаем на основе аннотаций или названии метода префикс, используемый в имени файла
     * или в качестве ключа других хранилищах кешируемых данных
     */

    protected String askPrefix(CacheAnnot an, Method method) {
        String prefix = "";
        if (an.fileNamePrefix().equals(""))
            prefix = method.getName();
        else
            prefix = an.fileNamePrefix();

        return prefix;
    }

    /**
     * @param file_name в котором хранятся закешированные данные
     * @return читает из файла, десериализует и возвращает объект DataObject, хранящий
     * десериализованные данные кеша
     */
    protected DataObject readFcFromFile(String file_name) {
        DataObject dataObject = null;

        try (FileInputStream fis = new FileInputStream(file_name);
             ObjectInputStream in = new ObjectInputStream(fis)
        ) {
            dataObject = (DataObject) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return dataObject;
    }


    /**
     * @param an
     * @param args
     * @return возвращаем список только тех аргументов, которые учитываются при определении уникальности результата
     */
    protected List<Object> getKeysFromArgs(CacheAnnot an, Object[] args) {
        List<Object> result = new ArrayList<>();
        if (an.identityBy().length == 0) return Arrays.asList(args);

        List<Class> list_type = new ArrayList<Class>(Arrays.asList(an.identityBy()));

        for (Object arg : args
                ) {

            if (list_type.remove(arg.getClass()))
                result.add(arg);
        }

        return result;
    }


    /**
     * Сериализация. Запись в файл.
     *
     * @param file_name
     * @param dataObject
     */
    protected void writeFcToFile(String file_name, DataObject dataObject) {
        try (FileOutputStream fos = new FileOutputStream(file_name);
             ObjectOutputStream out = new ObjectOutputStream(fos);
        ) {
            out.writeObject(dataObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
