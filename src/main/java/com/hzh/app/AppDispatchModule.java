package com.hzh.app;

import com.hzh.dagger.*;
import com.hzh.http.HttpMethod;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.muserver.MuRequest;
import io.muserver.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@Module
final public class AppDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(AppDispatchModule.class);

    @Provides
    @IntoMap
    @DispatchPath(value = "/hello", method = HttpMethod.GET)
    static Response dispatchHello(MuRequest request, HttpMethod method, @FormData RequestParameters requestParameters) {
        logger.info("calling hello {}", request.uri());
        logger.info("{}", requestParameters);
        logger.info("{}", method);
        return new Response(200, "Hello");
    }

    @Provides
    @IntoMap
    @DispatchPath("/world")
    static Response dispatchWorld(MuRequest request) {
        logger.info("calling world");
        try {
            return new Response(200, request.readBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response(555, "a");
    }

    @Provides
    @IntoMap
    @DispatchPath("/hello/{name}/{age}/{hobby}")
    static Response dispatchHelloName(MuRequest request, @PathValue Map<String, String> pathValue) {
        logger.info("calling helloName");
        return new Response(200, pathValue.toString());
    }
}