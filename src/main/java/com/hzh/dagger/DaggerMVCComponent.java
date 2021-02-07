package com.hzh.dagger;

import com.google.common.util.concurrent.ListenableFuture;
import dagger.BindsInstance;
import dagger.producers.ProductionComponent;

@ProductionComponent(modules = {DispatchModule.class, ExecutorModule.class})
interface DaggerMVCComponent {
  ListenableFuture<Response> request();
//  Executor executor();

  @ProductionComponent.Builder
  interface Builder {
    @BindsInstance
    Builder path(@Path Url path);
    DaggerMVCComponent build();
  }
}