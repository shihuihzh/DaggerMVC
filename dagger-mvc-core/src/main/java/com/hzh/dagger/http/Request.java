package com.hzh.dagger.http;

import com.google.common.collect.Maps;
import io.muserver.MuRequest;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final MuRequest rawRequest;
    private List<Cookie> cookiesCache;
    private final Map<String, Object> attributes;
    private QueryRequestParameters queryRequestParametersCache;
    private FormRequestParameters formRequestParametersCache;

    public Request(MuRequest rawRequest) {
        this.rawRequest = rawRequest;
        this.attributes = Maps.newConcurrentMap();
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

    public QueryRequestParameters query() {
        if (queryRequestParametersCache == null) {
            queryRequestParametersCache = new QueryRequestParameters(rawRequest.query());
        }

        return queryRequestParametersCache;
    }

    public FormRequestParameters form() {
        if (formRequestParametersCache == null) {
            try {
                formRequestParametersCache = new FormRequestParameters(rawRequest.form());
            } catch (IOException e) {
                DaggerMVCException.createAndThrow(e);
            }
        }

        return formRequestParametersCache;
    }

    public UploadedFile uploadedFile(String name) throws IOException {
        return new UploadedFile(rawRequest.uploadedFile(name));
    }

    public List<UploadedFile> uploadedFiles(String name) throws IOException {
        final List<io.muserver.UploadedFile> uploadedFiles = rawRequest.uploadedFiles(name);
        if (uploadedFiles != null && uploadedFiles.size() > 0) {
            return uploadedFiles.stream().map(UploadedFile::new).collect(Collectors.toList());
        }
        return null;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void getAttribute(String name) {
        attributes.get(name);
    }

    public void clearAttributes() {
        attributes.clear();
    }

    public void deleteAttribute(String name) {
        attributes.remove(name);
    }

    public Map<String, Object> getAllAttributes() {
        return attributes;
    }
}
