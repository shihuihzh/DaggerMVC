package com.hzh.app;

import com.hzh.dagger.annotation.ErrorHandler;
import com.hzh.dagger.http.HTMLResult;
import com.hzh.dagger.http.Request;
import com.hzh.dagger.http.Result;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class AppErrorHandlerModule {

    @IntoMap
    @Provides
    @ErrorHandler(404)
    static Result handle404(Request request) {
        return HTMLResult.newBuilder()
                .withHTML("I am a custom 404:" + request.uri())
                .build();
    }

    @IntoMap
    @Provides
    @ErrorHandler(405)
    static Result handle405(Request request) {
        return HTMLResult.newBuilder()
                .withHTML("Method not allow. I am a custom 405:" + request.uri())
                .build();
    }

    @IntoMap
    @Provides
    @ErrorHandler(500)
    static Result handle500(Exception exception) {
        return HTMLResult.newBuilder()
                .withHTML("I am a custom 500:" + exception.getMessage())
                .build();
    }
}
