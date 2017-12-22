package com.hw9.annotation;

import com.hw9.FileMemoryEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * с помощью аннотации указываем, какие методы кешировать и как
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAnnot {

    /**
     * @return FileMemoryEnum  enum FILE, IN_MEMORY. Определяем режим работы кеширования: просчитанный результат сериализовывать
     * в файле на диск или хранить в памяти JVM
     */
    FileMemoryEnum cacheType();

    /**
     * @return String это имя файла для сериализации Если по умолчанию, файл будет назван именем метода,
     * к которому относится аннотация
     */
    String fileNamePrefix() default "";

    /**
     * @return Class[] Это массив типов, который определяет какие аргументы метода учитывать
     * при определении уникальности результата,
     * а какие игнорировать(по умолчанию все аргументы учитываются)
     */
    Class[] identityBy() default {};
}
