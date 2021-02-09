package com.hzh.dagger.module;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hzh.dagger.annotation.DispatchPath;
import com.hzh.dagger.http.*;
import dagger.Module;
import dagger.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Module(includes = MVCModule.class)
public class CollectDispatcherModule {

    private static final Logger logger = LoggerFactory.getLogger(CollectDispatcherModule.class);
    private static final Pattern pathTokenPattern = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9]+)}");

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
    static PathValue pathValue(Map<Dispatcher, Provider<Response>> dispatchers, Request request) {

        String path = request.getRequest().uri().normalize().getPath();
        final PathValue pathValueMap = new PathValue();

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
}
