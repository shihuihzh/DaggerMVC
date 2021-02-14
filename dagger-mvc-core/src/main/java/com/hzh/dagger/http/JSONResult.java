package com.hzh.dagger.http;

import com.google.gson.Gson;

public class JSONResult extends Result {

    private static final Gson GSON = new Gson();

    public JSONResult(int statusCode, String contentType, String data, Cookie[] cookies) {
        super(statusCode, contentType, data, cookies);
    }

    public static JSONResultBuilder newBuilder() {
        return new JSONResultBuilder();
    }

    public static class JSONResultBuilder extends Builder<JSONResultBuilder> {

        private String data;
        public JSONResultBuilder withJsonString(String jsonString) {
            this.data = jsonString;
            return this;
        }

        public JSONResultBuilder withObject(Object object) {
            this.data = GSON.toJson(object);
            return this;
        }

        public JSONResult build() {
            return new JSONResult(this.statusCode, "application/json; charset=utf-8", data, cookies);
        }

    }
}
