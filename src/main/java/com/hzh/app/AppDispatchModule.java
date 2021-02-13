package com.hzh.app;

import com.hzh.dagger.annotation.DispatchPath;
import com.hzh.dagger.annotation.QueryParameter;
import com.hzh.dagger.http.*;
import com.hzh.dagger.module.CollectDispatcherModule;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.muserver.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Module(includes = CollectDispatcherModule.class)
final public class AppDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(AppDispatchModule.class);

    @Provides
    @IntoMap
    @DispatchPath(value = "/hello", method = HttpMethod.GET)
    static Result dispatchHello(Request request, HttpMethod method, @QueryParameter RequestParameters parameters) {
        logger.info("calling hello {}", request.getRawRequest().uri());
        logger.info("Method: {}", method);
        logger.info("Request parameter: {}", parameters);
        return new Result(200, "<h1>Hello</h1>");
    }

    @Provides
    @IntoMap
    @DispatchPath("/world")
    static Result dispatchWorld(Request request) {
        logger.info("calling world");
        try {
            return new Result(200, request.getRawRequest().readBodyAsString());
        } catch (IOException e) {
            return DaggerMVCException.createAndThrow(e);
        }
    }

    @Provides
    @IntoMap
    @DispatchPath("/exception")
    static Result dispatchException(Request request) {
        logger.info("calling throw exception");
        return DaggerMVCException.createAndThrow(new RuntimeException());
    }

    @Provides
    @IntoMap
    @DispatchPath("/hello/{name}/{age}/{hobby}")
    static Result dispatchHelloName(Request request, PathValueMap pathValue) {
        logger.info("calling helloName");
        return new Result(200, pathValue.toString());
    }

    @Provides
    @IntoMap
    @DispatchPath("/blank")
    static Result dispatchBlank() {
        logger.info("calling empty");
        return new Result(200, "blank");
    }

    @Provides
    @IntoMap
    @DispatchPath("/cookie")
    static Result dispatchCookie(List<Cookie> cookieList) {
        logger.info("calling cookie");
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Cookie</h1>");
        cookieList.forEach(c -> sb.append(c.getName()).append(": ").append(c.getValue()).append("<br>"));
        return new Result(200, sb.toString());
//        return new Result(200, "<h1>Hello</h1>");
    }
}