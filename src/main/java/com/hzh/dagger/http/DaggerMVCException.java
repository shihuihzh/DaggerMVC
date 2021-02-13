package com.hzh.dagger.http;

public class DaggerMVCException extends RuntimeException {

    public DaggerMVCException() { }

    public DaggerMVCException(Exception e) {
        super(e);
    }

    public static Result createAndThrow(Exception e) {
        throw new DaggerMVCException(e);
    }
}
