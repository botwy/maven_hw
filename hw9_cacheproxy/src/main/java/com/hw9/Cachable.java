package com.hw9;

public @interface Cachable {
    boolean persistent() default false;
}
