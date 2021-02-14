package com.hzh.dagger.http;

import io.muserver.MuRequest;
import io.muserver.MuResponse;

import java.net.URI;
import java.util.List;

public class MVCHolder {

    private final ThreadLocal<RequestContext> requestCtxHolder = new ThreadLocal<>();

    public void initRequestContext(MuRequest request, MuResponse response) {
        requestCtxHolder.set(new RequestContext(request, response));
    }

    public RequestContext getLocalRequestContext() {
        return requestCtxHolder.get();
    }

    public void finishRequest() {
        requestCtxHolder.remove();
    }


    public static class RequestContext {
        private final Request request;
        private final Response response;

        public RequestContext(MuRequest rawRequest, MuResponse rawResponse) {
            this.request = new Request(rawRequest);
            this.response = new Response(rawResponse);
        }

        public URI getURI() {
            return request.uri();
        }

        public Request getRequest() {
            return request;
        }

        public Response getResponse() {
            return response;
        }

        public HttpMethod getMethod() {
            return request.getMethod();
        }

        public List<Cookie> getCookies() {
            return request.getCookies();
        }
    }
}
