package com.hzh.dagger.http;

public class RedirectResult extends Result {

    private final String url;

    public RedirectResult(int statusCode, String url, String contentType, String data, Cookie[] cookies) {
        super(statusCode, contentType, data, cookies);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static RedirectResultBuilder newBuilder() {
        return new RedirectResultBuilder();
    }


    public static class RedirectResultBuilder extends Builder<RedirectResultBuilder> {

        private String text;
        private String url;
        private int statusCode = 302;

        @Override
        public RedirectResultBuilder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public RedirectResultBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public RedirectResultBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public RedirectResult build() {
            return new RedirectResult(statusCode, url, "text/plain; charset=utf-8", text, cookies);
        }

    }
}
