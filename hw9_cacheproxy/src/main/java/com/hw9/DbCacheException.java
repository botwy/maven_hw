package com.hw9;

public class DbCacheException extends Exception{

    public DbCacheException(String message) {
        super(message);
    }

    public DbCacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
