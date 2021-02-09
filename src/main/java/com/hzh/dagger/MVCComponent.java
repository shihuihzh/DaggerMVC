package com.hzh.dagger;

import com.hzh.dagger.http.Response;
import com.hzh.dagger.module.DefaultDispatchModule;
import dagger.Subcomponent;

@Subcomponent(modules = {DefaultDispatchModule.class})
public interface MVCComponent {
  Response request();
//  Executor executor();

  @Subcomponent.Builder
  interface Builder {
////    @BindsInstance Builder withQueryParameter(@QueryParameter RequestParameters parameters);
    MVCComponent build();
//
  }
}