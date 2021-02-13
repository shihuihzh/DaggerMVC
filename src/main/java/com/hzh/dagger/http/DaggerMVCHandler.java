package com.hzh.dagger.http;

import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;

public class DaggerMVCHandler implements MuHandler  {
    final RequestRouter requestRouter;

    public DaggerMVCHandler(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    @Override
    public boolean handle(MuRequest muRequest, MuResponse muResponse) throws Exception {

        final Result res = requestRouter.dispatch(muRequest, muResponse);
        muResponse.status(res.code);
        muResponse.writer().write(res.html);
//        Response response = Dagg.builder()
//                .withRequest(muRequest)
//                .build()
//                .request();
//
//        muResponse.writer().write(response.html);
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
