package com.hzh.dagger.module;


import com.hzh.dagger.annotation.PathValue;
import com.hzh.dagger.http.HttpMethod;
import com.hzh.dagger.http.MVCHolder;
import com.hzh.dagger.http.Request;
import com.hzh.dagger.annotation.FormData;
import com.hzh.dagger.annotation.QueryParameter;
import dagger.Module;
import dagger.Provides;
import io.muserver.RequestParameters;

import java.io.IOException;
import java.util.Map;

@Module
public interface MVCModule {

    @Provides
    static Request provideRequest(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequest();
    }

    @Provides
    static HttpMethod provideMethod(MVCHolder mvcHolder) {
        return mvcHolder.getLocalMethod();
    }

    @Provides
    @QueryParameter
    static RequestParameters provideQueryParameters(MVCHolder mvcHolder) {
        return mvcHolder.getLocalRequest().getRequest().query();
    }

    @Provides
    @FormData
    static RequestParameters provideFormData(MVCHolder mvcHolder) {
        try {
            return mvcHolder.getLocalRequest().getRequest().form();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @PathValue
    static Map<String, String> providePathValueMap(com.hzh.dagger.http.PathValue pathValue) {
        return pathValue.toMap();
    }
}
