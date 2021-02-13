package com.hzh.dagger.http;

public class Result {
    private final int statusCode;
    private final Object data;
    private final String contentType;

    public Result(int statusCode, String contentType, Object data) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.data = data;
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

    public static class Builder {

        private int statusCode = 200;
        private String data;
        private String contentType;

        public Builder withStatusCode(int code) {
            this.statusCode = code;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder withData(String data) {
            this.data = data;
            return this;
        }

        public HTMLResult build() {
            return new HTMLResult(statusCode, contentType, data);
        }

        public static  Builder newBuilder() {
            return new Builder();
        }
    }
}
