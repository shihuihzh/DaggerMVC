package com.hzh.dagger.http;

import io.muserver.MuRequest;
import io.muserver.MuResponse;

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

}
