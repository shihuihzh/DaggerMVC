package com.hzh.dagger;

import com.google.common.util.concurrent.ListenableFuture;
import dagger.BindsInstance;
import dagger.producers.ProductionComponent;
import io.muserver.MuRequest;

@ProductionComponent(modules = {DispatchModule.class, ExecutorModule.class})
public interface DaggerMVCComponent {
  ListenableFuture<Response> request();
//  Executor executor();

  @ProductionComponent.Builder
  interface Builder {
    @BindsInstance
    Builder withRequest(MuRequest request);
    DaggerMVCComponent build();
  }
}