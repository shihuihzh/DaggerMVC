package com.hzh.dagger.http;

import io.muserver.MuRequest;

public class MVCHolder {

    private final ThreadLocal<HttpMethod> methodHolder = new ThreadLocal<>();
    private final ThreadLocal<Request> requestHolder = new ThreadLocal<>();

    public void setRequest(MuRequest request) {
        methodHolder.set(HttpMethod.of(request.method().toString()));
        requestHolder.set(new Request(request));
    }

    public HttpMethod getLocalMethod() {
        return methodHolder.get();
    }

    public Request getLocalRequest() {
        return requestHolder.get();
    }
}
