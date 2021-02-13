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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return HTMLResult.Builder.newBuilder()
                .withStatusCode(200)
                .withData("<h1>Hello</h1>")
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/world")
    static Result dispatchWorld(Request request) {
        logger.info("calling world");
        try {
            return HTMLResult.Builder.newBuilder()
                    .withStatusCode(200)
                    .withData(request.getRawRequest().readBodyAsString())
                    .build();
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
        return HTMLResult.Builder.newBuilder()
                .withStatusCode(200)
                .withData(pathValue.toString())
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/blank")
    static Result dispatchBlank() {
        logger.info("calling empty");
        return HTMLResult.Builder.newBuilder()
                .withStatusCode(200)
                .withData("Hello")
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/cookie")
    static Result dispatchCookie(Request request, List<Cookie> cookieList) {
        logger.info("calling cookie");
        logger.info("cookie: a=" + request.getCookie("a").getValue());
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Cookie</h1>");
        cookieList.forEach(c -> sb.append(c.getName()).append(": ").append(c.getValue()).append("<br>"));
        return HTMLResult.Builder.newBuilder()
                .withStatusCode(200)
                .withData(sb.toString())
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/json")
    static Result dispatchJson() {
        logger.info("calling json");
        Map<String, Object> obj = new HashMap<>();
        obj.put("name", "Howe");
        obj.put("age", 30);
        obj.put("hobby", "computer");

        return JSONResult.Builder.newBuilder()
                .withStatusCode(200)
                .withObject(obj)
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/download")
    static Result dispatchDownload() {
        logger.info("calling dowload");
        return FileResult.Builder.newBuilder()
                .withStatusCode(200)
                .withFile(new File(AppDispatchModule.class.getResource("/downloadme.bin").getFile()))
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/download-mp4")
    static Result dispatchDownloadMP4() {
        logger.info("calling dowload");
        return FileResult.Builder.newBuilder()
                .withStatusCode(200)
                .withFile(new File(AppDispatchModule.class.getResource("/downloadme.mp4").getFile()))
                .build();
    }
}