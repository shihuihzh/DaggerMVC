package com.hzh.dagger.module;

import com.hzh.dagger.http.*;
import dagger.BindsOptionalOf;
import dagger.Module;
import dagger.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Module
public abstract class DefaultDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatchModule.class);

    static Result defaultResult404(Request request) {
        return HTMLResult.newBuilder()
                .withStatusCode(404)
                .withHTML("<h1>page not found</h1>" + request.uri().getPath())
                .build();

    }

    static Result defaultResult405(Request request) {
        return HTMLResult.newBuilder()
                .withStatusCode(405)
                .withHTML("<h1>Method Not Allowed</h1>")
                .build();
    }

    static Result defaultResult500(Request request, Exception e) {
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
                .withHTML("<h1>Internal Server Error</h1><pre>" + exceptionMessage + "</pre>")
                .build();
    }

    @BindsOptionalOf abstract Map<Integer, Provider<Result>> optionalErrorHandler();

    @Provides
    static Result dispatch(Map<Dispatcher, Provider<Result>> dispatchers,
                           Optional<Map<Integer, Provider<Result>>> errorHandler,
                           HttpMethod method, RequestContext requestContext, Request request, URI normalizeURI) {

        String path = normalizeURI.getPath();
        logger.debug("request uri path={}, normalize={}", request.uri().getPath(), path);

        try {
            for (Map.Entry<Dispatcher, Provider<Result>> dispatcherEntry : dispatchers.entrySet()) {
                final Dispatcher dispatcher = dispatcherEntry.getKey();
                if (dispatcher.getPathPatten().matcher(path).find()) {
                    if (dispatcher.getMethod() == HttpMethod.ALL || dispatcher.getMethod() == method) {
                        return dispatcherEntry.getValue().get();
                    } else {
                        return getErrorResult(405, null, errorHandler, requestContext);
                    }
                }
            }

            return getErrorResult(404, null, errorHandler, requestContext);

        } catch (Exception e) {
            logger.debug(String.format("Request %s got exception.", path), e);
            return getErrorResult(500, e, errorHandler, requestContext);
        }
    }

    private static Result getErrorResult(int statusCode, Exception exception,
                                         Optional<Map<Integer, Provider<Result>>> errorHandler,
                                         RequestContext requestContext) {

        if (exception != null) {
            requestContext.setRequestException(exception);
        }

        if (errorHandler.isPresent() && errorHandler.get().containsKey(statusCode)) {
            return errorHandler.get().get(statusCode).get();
        } else {
            switch (statusCode) {
                case 404:
                    return defaultResult404(requestContext.getRequest());
                case 405:
                    return defaultResult405(requestContext.getRequest());
                case 500:
                    return defaultResult500(requestContext.getRequest(), exception);
            }
        }

        return null;
    }

}