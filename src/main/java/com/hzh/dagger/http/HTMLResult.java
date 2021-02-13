package com.hzh.dagger.http;

public class HTMLResult extends Result {

    public HTMLResult(int statusCode, String contentType, String data) {
        super(statusCode, contentType, data);
    }


    public static class Builder {

        private int statusCode = 200;
        private String data;

        public Builder withStatusCode(int code) {
            this.statusCode = code;
            return this;
        }

        public Builder withData(String data) {
            this.data = data;
            return this;
        }

        public HTMLResult build() {
            return new HTMLResult(statusCode, "text/html", data);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

    }
}
