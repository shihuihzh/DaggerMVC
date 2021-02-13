package com.hzh.dagger.module;


import com.hzh.dagger.annotation.FormData;
import com.hzh.dagger.annotation.PathValue;
import com.hzh.dagger.annotation.QueryParameter;
import com.hzh.dagger.http.*;
import dagger.Module;
import dagger.Provides;
import io.muserver.RequestParameters;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Module
public interface MVCModule {

    @Provides
    static Request provideRequest(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest();
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
    @QueryParameter
    static RequestParameters provideQueryParameters(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequestContext().getRequest().getRawRequest().query();
    }

    @Provides
    @FormData
    static RequestParameters provideFormData(MVCHolder mvcHolder) {
        try {
            return mvcHolder.getLocalRequestContext().getRequest().getRawRequest().form();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @PathValue
    static Map<String, String> providePathValueMap(PathValueMap pathValue) {
        return pathValue.toMap();
    }
}
