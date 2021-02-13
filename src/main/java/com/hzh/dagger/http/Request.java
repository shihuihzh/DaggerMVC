package com.hzh.dagger.http;

import io.muserver.MuRequest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Request {
    private final MuRequest rawRequest;
    private List<Cookie> cookiesCache;

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
        if (cookiesCache == null) {
            cookiesCache = rawRequest.cookies().stream().map(Cookie::new).collect(Collectors.toList());
        }

        return cookiesCache;
    }

    public Cookie getCookie(String name) {
        final List<Cookie> cookies = getCookies();
        return cookies.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
