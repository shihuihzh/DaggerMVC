package com.hzh.dagger.http;

public class HTMLResult extends Result {

    public HTMLResult(int statusCode, String contentType, String data, Cookie[] cookies) {
        super(statusCode, contentType, data, cookies);
    }

    public static HTMLResultBuilder newBuilder() {
        return new HTMLResultBuilder();
    }


    public static class HTMLResultBuilder extends Result.Builder<HTMLResultBuilder> {

        private String data;

        public HTMLResultBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public HTMLResult build() {
            return new HTMLResult(statusCode, "text/html", data, cookies);
        }

    }
}
