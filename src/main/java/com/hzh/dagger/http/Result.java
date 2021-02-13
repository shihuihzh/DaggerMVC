package com.hzh.dagger.http;

public class Result {
    public String html;
    public int code;

    public Result(int code, String html) {
        this.code = code;
        this.html = html;
    }
}
