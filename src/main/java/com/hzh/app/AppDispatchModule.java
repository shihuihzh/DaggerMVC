package com.hzh.app;

import com.hzh.dagger.annotation.DispatchPath;
import com.hzh.dagger.annotation.QueryParameter;
import com.hzh.dagger.http.HttpMethod;
import com.hzh.dagger.http.PathValue;
import com.hzh.dagger.http.Request;
import com.hzh.dagger.http.Response;
import com.hzh.dagger.module.CollectDispatcherModule;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.muserver.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Module(includes = CollectDispatcherModule.class)
final public class AppDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(AppDispatchModule.class);

    @Provides
    @IntoMap
    @DispatchPath(value = "/hello", method = HttpMethod.GET)
    static Response dispatchHello(Request request, HttpMethod method, @QueryParameter RequestParameters parameters) {
        logger.info("calling hello {}", request.getRequest().uri());
        logger.info("Method: {}", method);
        logger.info("Request parameter: {}", parameters);
        return new Response(200, "Hello");
    }

    @Provides
    @IntoMap
    @DispatchPath("/world")
    static Response dispatchWorld(Request request) {
        logger.info("calling world");
        try {
            return new Response(200, request.getRequest().readBodyAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @IntoMap
    @DispatchPath("/hello/{name}/{age}/{hobby}")
    static Response dispatchHelloName(Request request, PathValue pathValue) {
        logger.info("calling helloName");
        return new Response(200, pathValue.toString());
    }

    @Provides
    @IntoMap
    @DispatchPath("/blank")
    static Response dispatchBlank() {
        logger.info("calling empty");
        return new Response(200, "blank");
    }
}