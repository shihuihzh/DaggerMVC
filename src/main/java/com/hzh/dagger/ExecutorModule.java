package com.hzh.dagger;

import dagger.Module;
import dagger.Provides;
import dagger.producers.Production;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Module
final class ExecutorModule {

  public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

  @Provides
  @Production
  static Executor executor() {
    return EXECUTOR_SERVICE;
  }
}