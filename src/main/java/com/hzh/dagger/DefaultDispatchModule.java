package com.hzh.dagger;

import com.hzh.http.HttpMethod;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.muserver.MuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.util.Map;

@Module
final public class DefaultDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatchModule.class);
    public static final String DAGGER_MVC_NOT_FOUND = "NOT_FOUND";
    private static final String DAGGER_MVC_METHOD_NOT_ALLOWED = "DAGGER_MVC_METHOD_NOT_ALLOWED";

    @Provides
    @IntoMap
    @DispatchPath(DAGGER_MVC_NOT_FOUND)
    static Response return404(MuRequest request) {
        return new Response(404, "page not found: " + request.uri().getPath());
    }

    @Provides
    @IntoMap
    @DispatchPath(DAGGER_MVC_METHOD_NOT_ALLOWED)
    static Response return405(MuRequest request) {
        return new Response(405, "Method Not Allowed");
    }

    @Provides
    static Response dispatch(Map<DispatchPath, Provider<Response>> dispatchers,
                             Map<GetPath, Provider<Response>> getDispatchers,
                             HttpMethod method, MuRequest request) {
        logger.debug("request uri path: {}", request.uri().getPath());

        String path = request.uri().getPath();

        for (Map.Entry<GetPath, Provider<Response>> dispatcherEntry : getDispatchers.entrySet()) {
            final GetPath dispatchPath = dispatcherEntry.getKey();
            if (dispatchPath.value().equals(path)) {
                return dispatcherEntry.getValue().get();
            }
        }

        for (Map.Entry<DispatchPath, Provider<Response>> dispatcherEntry : dispatchers.entrySet()) {
            final DispatchPath dispatchPath = dispatcherEntry.getKey();
            if (dispatchPath.value().equals(path)) {
                if (dispatchPath.method() == HttpMethod.ALL || dispatchPath.method() == method) {
                    return dispatcherEntry.getValue().get();
                } else {
                    return return405(request);
                }
            }
        }

        return return404(request);
    }

}