package com.hzh.dagger.http;

import java.io.InputStream;

public class StreamResult extends Result {

    public StreamResult(int statusCode, String contentType, InputStream inputStream) {
        super(statusCode, contentType, inputStream);
    }

    public static class Builder {

        private int statusCode = 200;
        private InputStream inputStream;

        public Builder withStatusCode(int code) {
            this.statusCode = code;
            return this;
        }

        public Builder withInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public StreamResult build() {
            String contentType = "application/octet-stream";
            return new StreamResult(statusCode, contentType, inputStream);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

    }
}
