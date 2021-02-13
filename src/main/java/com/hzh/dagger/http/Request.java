package com.hzh.dagger.http;

import io.muserver.MuRequest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Request {
    private final MuRequest rawRequest;

    public Request(MuRequest rawRequest) {
        this.rawRequest = rawRequest;
    }

    public MuRequest getRawRequest() {
        return rawRequest;
    }

    public URI uri() {
        return this.rawRequest.uri();
    }

    public HttpMethod getMethod() {
        return HttpMethod.of(rawRequest.method().toString());
    }

    public List<Cookie> getCookies() {
        return rawRequest.cookies().stream()
                .map(Cookie::new).collect(Collectors.toList());
    }

}
