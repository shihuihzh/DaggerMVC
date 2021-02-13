package com.hzh.dagger.http;

import com.hzh.dagger.MVCComponent;
import io.muserver.MuRequest;
import io.muserver.MuResponse;

import javax.inject.Inject;
import javax.inject.Provider;

public class RequestRouter {

    private final Provider<MVCComponent.Builder> mvcComponentProvider;
    private final MVCHolder holder;

    @Inject RequestRouter(Provider<MVCComponent.Builder> mvcComponentProvider, MVCHolder holder) {
        this.mvcComponentProvider = mvcComponentProvider;
        this.holder = holder;
    }

    public Result dispatch(MuRequest muRequest, MuResponse muResponse) throws Exception {
        holder.initRequestContext(muRequest, muResponse);
        final Result response = mvcComponentProvider.get()
                .build().request();

        holder.finishRequest();

        return response;
    }
}
