package com.hzh.dagger.module;


import com.hzh.dagger.annotation.PathValue;
import com.hzh.dagger.http.*;
import dagger.Module;
import dagger.Provides;
import io.muserver.MuRequest;
import io.muserver.MuResponse;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Module
public interface MVCModule {

    @Provides
    static MVCHolder.RequestContext provideRequestContext(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext();
    }

    @Provides
    static Request provideRequest(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest();
    }

    @Provides
    static Response provideResponse(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getResponse();
    }

    @Provides
    static MuRequest provideRawRequest(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest().getRawRequest();
    }

    @Provides
    static MuResponse provideRawResponse(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getResponse().getRawResponse();
    }

    @Provides
    static HttpMethod provideMethod(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getMethod();
    }

    @Provides
    static URI provideRequestNormalizeURI(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getURI().normalize();
    }

    @Provides
    static List<Cookie> provideRequestCookies(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getCookies();
    }

    @Provides
    static QueryRequestParameters provideQueryParameters(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest().query();
    }

    @Provides
    static FormRequestParameters provideFormData(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest().form();
    }


    @Provides
    @PathValue
    static Map<String, String> providePathValueMap(PathValueMap pathValue) {
        return pathValue.toMap();
    }
}
