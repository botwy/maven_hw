package com.hw9.exception;

public class DbCacheException extends Exception{

    public DbCacheException(String message) {
        super(message);
    }

    public DbCacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
