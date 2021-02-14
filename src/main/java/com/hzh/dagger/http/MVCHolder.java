package com.hzh.dagger.http;

import io.muserver.MuRequest;
import io.muserver.MuResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public class MVCHolder {

    private final ThreadLocal<RequestContext> requestCtxHolder = new ThreadLocal<>();

    public RequestContext initRequestContext(MuRequest request, MuResponse response) {
        final RequestContext context = new RequestContext(request, response);
        requestCtxHolder.set(context);
        return context;
    }

    public RequestContext getLocalRequestContext() {
        return requestCtxHolder.get();
    }

    public void finishRequest() {
        requestCtxHolder.remove();
    }


    public static class RequestContext {
        private final String requestId;
        private final Request request;
        private final Response response;

        public RequestContext(MuRequest rawRequest, MuResponse rawResponse) {
            this.requestId = UUID.randomUUID().toString();
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

        public String getRequestId() {
            return requestId;
        }
    }
}
