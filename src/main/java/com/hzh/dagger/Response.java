package com.hzh.dagger;

public class Response {
    public String html;
    public int code;

    public Response(int code, String html) {
        this.code = code;
        this.html = html;
    }
}
