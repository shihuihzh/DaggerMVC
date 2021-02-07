package com.hzh.http;

public enum HttpMethod {
    ALL,
    GET,
    POST,
    HEAD,
    OPTIONS,
    PUT,
    DELETE,
    TRACE,
    CONNECT,
    PATCH;

    public static HttpMethod of(String methodName) {
        for (HttpMethod m : values()) {
            if (m.toString().equalsIgnoreCase(methodName)) {
                return m;
            }
        }
        throw new RuntimeException("Method not found");
    }
}

