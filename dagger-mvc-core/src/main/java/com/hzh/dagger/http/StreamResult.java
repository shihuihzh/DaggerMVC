package com.hzh.dagger.http;

import java.io.InputStream;

public class StreamResult extends Result {

    public StreamResult(int statusCode, String contentType, InputStream inputStream, Cookie[] cookies) {
        super(statusCode, contentType, inputStream, cookies);
    }

    public static StreamResultBuilder newBuilder() {
        return new StreamResultBuilder();
    }


    public static class StreamResultBuilder extends Builder<StreamResultBuilder> {

        private InputStream inputStream;

        public StreamResultBuilder withInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public StreamResult build() {
            return new StreamResult(statusCode, "application/octet-stream", inputStream, cookies);
        }

    }
}
