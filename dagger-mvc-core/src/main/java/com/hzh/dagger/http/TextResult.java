package com.hzh.dagger.http;

public class TextResult extends Result {

    public TextResult(int statusCode, String contentType, String data, Cookie[] cookies) {
        super(statusCode, contentType, data, cookies);
    }

    public static TextResultBuilder newBuilder() {
        return new TextResultBuilder();
    }


    public static class TextResultBuilder extends Builder<TextResultBuilder> {

        private String text;

        public TextResultBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public TextResult build() {
            return new TextResult(statusCode, "text/plain; charset=utf-8", text, cookies);
        }

    }
}
