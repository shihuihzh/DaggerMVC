package com.hzh.http;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hzh.dagger.*;
import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;

public class DaggerMVCHandler implements MuHandler  {
    @Override
    public boolean handle(MuRequest muRequest, MuResponse muResponse) throws Exception {

        ListenableFuture<Response> response = DaggerDaggerMVCComponent.builder()
                .withRequest(muRequest)
                .build()
                .request();

        Response resp = response.get();
        muResponse.writer().write(resp.html);
//        Futures.addCallback(response, new FutureCallback<Response>() {
//            @Override
//            public void onSuccess(Response result) {
//                muResponse.writer().write(result.html);
//                muResponse.
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                t.printStackTrace();
//            }
//        }, ExecutorModule.EXECUTOR_SERVICE);
        return true;
    }
}
