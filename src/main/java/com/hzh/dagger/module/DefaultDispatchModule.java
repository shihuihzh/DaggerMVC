package com.hzh.dagger.module;

import com.hzh.dagger.http.Dispatcher;
import com.hzh.dagger.http.HttpMethod;
import com.hzh.dagger.http.Request;
import com.hzh.dagger.http.Response;
import dagger.Module;
import dagger.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.util.Map;

@Module
final public class DefaultDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatchModule.class);

//    public static final String DAGGER_MVC_NOT_FOUND = "NOT_FOUND";
//    private static final String DAGGER_MVC_METHOD_NOT_ALLOWED = "DAGGER_MVC_METHOD_NOT_ALLOWED";

    static Response return404(Request request) {
        return new Response(404, "page not found: " + request.uri().getPath());
    }

    static Response return405(Request request) {
        return new Response(405, "Method Not Allowed");
    }


    @Provides
    static Response dispatch(Map<Dispatcher, Provider<Response>> dispatchers,
                             HttpMethod method, Request request) {

        String path = request.uri().normalize().getPath();
        logger.debug("request uri path={}, normalize={}", request.uri().getPath(), path);

        for (Map.Entry<Dispatcher, Provider<Response>> dispatcherEntry : dispatchers.entrySet()) {
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
    }

}