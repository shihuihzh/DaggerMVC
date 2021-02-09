package com.hzh.dagger.http;

import io.muserver.MuRequest;

import java.net.URI;

public class Request {
    private MuRequest request;

    public Request(MuRequest request) {
        this.request = request;
    }

    public MuRequest getRequest() {
        return request;
    }

    public URI uri() {
        return this.request.uri();
    }
}
