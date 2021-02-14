package com.hzh.dagger.module;

import com.hzh.dagger.http.*;
import dagger.Module;
import dagger.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.net.URI;
import java.util.Map;

@Module
final public class DefaultDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatchModule.class);

//    public static final String DAGGER_MVC_NOT_FOUND = "NOT_FOUND";
//    private static final String DAGGER_MVC_METHOD_NOT_ALLOWED = "DAGGER_MVC_METHOD_NOT_ALLOWED";

    static Result return404(Request request) {
        return HTMLResult.newBuilder()
                .withStatusCode(404)
                .withData("<h1>page not found</h1>" + request.uri().getPath())
                .build();

    }

    static Result return405(Request request) {
        return HTMLResult.newBuilder()
                .withStatusCode(405)
                .withData("<h1>Method Not Allowed</h1>")
                .build();
    }

    static Result return500(Request request, Exception e) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(e.toString()).append("\n");
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            exceptionMessage
                    .append("    at ")
                    .append(stackTraceElement.toString())
                    .append("\n");

        }

        return HTMLResult.newBuilder()
                .withStatusCode(500)
                .withData("<h1>Internal Server Error</h1><pre>" + exceptionMessage + "</pre>")
                .build();
    }

    @Provides
    static Result dispatch(Map<Dispatcher, Provider<Result>> dispatchers,
                           HttpMethod method, Request request, URI normalizeURI) {

        String path = normalizeURI.getPath();
        logger.debug("request uri path={}, normalize={}", request.uri().getPath(), path);

        try {
            for (Map.Entry<Dispatcher, Provider<Result>> dispatcherEntry : dispatchers.entrySet()) {
                final Dispatcher dispatcher = dispatcherEntry.getKey();
                if (dispatcher.getPathPatten().matcher(path).find()) {
                    if (dispatcher.getMethod() == HttpMethod.ALL || dispatcher.getMethod() == method) {
                        return dispatcherEntry.getValue().get();
                    } else {
                        return return405(request);
                    }
                }
            }
            return return404(request);
        } catch (Exception e) {
            logger.debug(String.format("Request %s got exception.", path), e);
            return return500(request, e);
        }
    }

}