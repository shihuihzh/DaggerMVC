package com.hzh.dagger.http;

import io.muserver.MuRequest;

import java.net.URI;

public class Request {
    private MuRequest rawRequest;

    public Request(MuRequest rawRequest) {
        this.rawRequest = rawRequest;
    }

    public MuRequest getRawRequest() {
        return rawRequest;
    }

    public URI uri() {
        return this.rawRequest.uri();
    }
}
