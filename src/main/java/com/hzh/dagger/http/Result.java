package com.hzh.dagger.http;

public class Result {
    private final int statusCode;
    private final Object data;
    private final String contentType;
    private final Cookie[] cookies;


    public Result(int statusCode, String contentType, Object data, Cookie[] cookies) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.data = data;
        this.cookies = cookies;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public static class Builder<T extends Builder> {

        protected int statusCode = 200;
        protected String data;
        protected String contentType;
        protected Cookie[] cookies;

        public T withStatusCode(int code) {
            this.statusCode = code;
            return (T) this;
        }

        public T withContentType(String contentType) {
            this.contentType = contentType;
            return (T) this;
        }

        public T withData(String data) {
            this.data = data;
            return (T) this;
        }

        public T withCookie(Cookie... cookies) {
            this.cookies = cookies;
            return (T) this;
        }

        public Result build() {
            return new Result(statusCode, contentType, data, cookies);
        }

    }
}
