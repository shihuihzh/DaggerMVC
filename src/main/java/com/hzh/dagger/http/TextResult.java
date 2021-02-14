package com.hzh.dagger.http;

public class TextResult extends Result {

    public TextResult(int statusCode, String contentType, String data, Cookie[] cookies) {
        super(statusCode, contentType, data, cookies);
    }

    public static TextResultBuilder newBuilder() {
        return new TextResultBuilder();
    }


    public static class TextResultBuilder extends Result.Builder<TextResultBuilder> {

        private String data;

        public TextResultBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public TextResult build() {
            return new TextResult(statusCode, "text/plain", data, cookies);
        }

    }
}