package com.hzh.dagger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hzh.http.Dispatcher;
import com.hzh.http.HttpMethod;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.muserver.MuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Module
final public class DefaultDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatchModule.class);
    final static Pattern pathTokenPattern = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9]+)}");
    public static final String DAGGER_MVC_NOT_FOUND = "NOT_FOUND";
    private static final String DAGGER_MVC_METHOD_NOT_ALLOWED = "DAGGER_MVC_METHOD_NOT_ALLOWED";
//    private static final DispatchPath RETURN_404 = DispatchPathCreator.createDispatchPath(DAGGER_MVC_NOT_FOUND, HttpMethod.ALL);
//    private static final DispatchPath RETURN_405 = DispatchPathCreator.createDispatchPath(DAGGER_MVC_METHOD_NOT_ALLOWED, HttpMethod.ALL);

//    @Provides
//    @IntoMap
//    @DispatchPath(DAGGER_MVC_NOT_FOUND)
    static Response return404(MuRequest request) {
        return new Response(404, "page not found: " + request.uri().getPath());
    }

//    @Provides
//    @IntoMap
//    @DispatchPath(DAGGER_MVC_METHOD_NOT_ALLOWED)
    static Response return405(MuRequest request) {
        return new Response(405, "Method Not Allowed");
    }

    @Provides
    @Singleton
    static Map<Dispatcher, Provider<Response>> dispatchersToRegex(Map<DispatchPath, Provider<Response>> dispatchers) {
        logger.debug("Transforming Dispatcher");
        Map<Dispatcher, Provider<Response>> dispatcherProviderMap = new HashMap<>();
        dispatchers.forEach((dispatchPath, pResponse) -> {
            final HttpMethod method = dispatchPath.method();
            final String path = dispatchPath.value();
            List<String> pathToken = Lists.newArrayList();

            final Matcher matcher = pathTokenPattern.matcher(path);
            while(matcher.find()) {
                pathToken.add(matcher.group(1));
            }

            final Pattern pathPattern = Pattern.compile("^" + matcher.replaceAll("(.*)") + "$");
            final Dispatcher dispatcher = new Dispatcher(path, method, pathPattern, pathToken);

            logger.debug("Source dispatcher={}, Target dispatcher={}", dispatchPath, dispatcher);
            dispatcherProviderMap.put(dispatcher, pResponse);

        });

        return dispatcherProviderMap;
    }

    @Provides
    @PathValue
    static Map<String, String> pathValue(Map<Dispatcher, Provider<Response>> dispatchers, MuRequest request) {

        String path = request.uri().normalize().getPath();
        final HashMap<String, String> pathValueMap = Maps.newHashMap();

        logger.debug("Filling path value for path={}", path);
        for (Dispatcher dispatcher: dispatchers.keySet()) {
            final Matcher matcher = dispatcher.getPathPatten().matcher(path);
            if (matcher.find()) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    pathValueMap.put(dispatcher.getPathToken().get(i), matcher.group(i+1));
                }

                break;
            }
        }

        return pathValueMap;
    }
    @Provides
    static Response dispatch(Map<Dispatcher, Provider<Response>> dispatchers,
                             HttpMethod method, MuRequest request) {

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