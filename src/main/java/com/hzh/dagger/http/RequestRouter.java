package com.hzh.dagger.http;

import com.hzh.dagger.MVCComponent;
import io.muserver.MuRequest;

import javax.inject.Inject;
import javax.inject.Provider;

public class RequestRouter {

    private final Provider<MVCComponent.Builder> mvcComponentProvider;
    private final MVCHolder holder;

    @Inject RequestRouter(Provider<MVCComponent.Builder> mvcComponentProvider, MVCHolder holder) {
        this.mvcComponentProvider = mvcComponentProvider;
        this.holder = holder;
    }

    public Response dispatch(MuRequest muRequest) throws Exception {
        holder.setRequest(muRequest);
        return mvcComponentProvider.get()
                .build().request();
    }

}
