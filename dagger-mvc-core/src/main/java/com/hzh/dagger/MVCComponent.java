package com.hzh.dagger;

import com.hzh.dagger.http.Result;
import com.hzh.dagger.module.DefaultDispatchModule;
import dagger.Subcomponent;

@Subcomponent(modules = {DefaultDispatchModule.class})
public interface MVCComponent {
  Result request() throws Exception;
//  Executor executor();

  @Subcomponent.Builder
  interface Builder {
////    @BindsInstance Builder withQueryParameter(@QueryParameter RequestParameters parameters);
    MVCComponent build();
//
  }
}