package com.hzh.dagger.http;

import com.google.gson.Gson;

public class JSONResult extends Result {

    private static final Gson GSON = new Gson();

    public JSONResult(int statusCode, String contentType, String data) {
        super(statusCode, contentType, data);
    }

    public static class Builder {

        private int statusCode = 200;
        private String data;

        public Builder withStatusCode(int code) {
            this.statusCode = code;
            return this;
        }

        public Builder withJsonString(String jsonString) {
            this.data = data;
            return this;
        }

        public Builder withObject(Object object) {
            this.data = GSON.toJson(object);
            return this;
        }

        public JSONResult build() {
            return new JSONResult(statusCode, "application/json", data);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

    }
}
