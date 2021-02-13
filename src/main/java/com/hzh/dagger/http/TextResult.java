package com.hzh.dagger.http;

public class TextResult extends Result {

    public TextResult(int statusCode, String contentType, String data) {
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

        public TextResult build() {
            return new TextResult(statusCode, "text/plain", data);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

    }
}
