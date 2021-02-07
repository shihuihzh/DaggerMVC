package com.hzh.dagger;

import com.google.common.util.concurrent.ListenableFuture;
import dagger.multibindings.IntoMap;
import dagger.producers.Producer;
import dagger.producers.ProducerModule;
import dagger.producers.Produces;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ProducerModule
final public class DispatchModule {
  private static final Logger logger = LoggerFactory.getLogger(DispatchModule.class);
  public static final String DAGGER_MVC_NOT_FOUND = "NOT_FOUND";

  @Produces
  @IntoMap
  @DispatchPath("/hello")
  static Response dispatchHello(MuRequest request) {
    System.out.println(request.uri());
    return new Response(200, "Hello");
  }


  @Produces
  @IntoMap
  @DispatchPath(DAGGER_MVC_NOT_FOUND)
  static Response return404(MuRequest request) {
    System.out.println(request.uri());
    return new Response(404, "not found");
  }

  @Produces
  static ListenableFuture<Response> dispatch(Map<String, Producer<Response>> dispatchers, MuRequest request) {
    logger.info("request uri path: {}", request.uri().getPath());
    if (dispatchers.containsKey(request.uri().getPath())) {
      return dispatchers.get(request.uri().getPath()).get();
    } else {
      return dispatchers.get(DAGGER_MVC_NOT_FOUND).get();
    }
  }
}