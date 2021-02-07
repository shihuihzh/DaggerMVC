package com.hzh.dagger;

import com.google.common.util.concurrent.ListenableFuture;
import dagger.multibindings.IntoMap;
import dagger.producers.Producer;
import dagger.producers.ProducerModule;
import dagger.producers.Produces;

import java.util.Map;

@ProducerModule
final class DispatchModule {
  @Produces
  @IntoMap
  @DispatchPath("/hello")
  static Response dispatchHello() {
    return new Response("Hello");
  }

  @Produces
  static ListenableFuture<Response> dispatch(Map<String, Producer<Response>> dispatchers, Url url) {
    System.out.println(url.path());
    return dispatchers.get(url.path()).get();
  }
}