package com.hzh.dagger.http;

import com.hzh.dagger.MVCComponent;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

public class RequestRouter {

    private static final Logger logger = LoggerFactory.getLogger(RequestRouter.class);

    private final Provider<MVCComponent.Builder> mvcComponentProvider;
    private final MVCHolder holder;

    @Inject RequestRouter(Provider<MVCComponent.Builder> mvcComponentProvider, MVCHolder holder) {
        this.mvcComponentProvider = mvcComponentProvider;
        this.holder = holder;
    }

    public Result dispatch(MuRequest muRequest, MuResponse muResponse) throws Exception {
        final RequestContext requestContext = holder.initRequestContext(muRequest, muResponse);

        logger.debug("Start to request, id=" + requestContext.getRequestId());
        final Result response = mvcComponentProvider.get()
                .build().request();

        holder.finishRequest();
        logger.debug("Request done, id=" + requestContext.getRequestId());

        return response;
    }
}
