package com.hzh.dagger;

import com.hzh.app.AppDispatchModule;
import com.hzh.http.HttpMethod;
import dagger.BindsInstance;
import dagger.Subcomponent;
import io.muserver.MuRequest;
import io.muserver.RequestParameters;

import javax.inject.Singleton;

@Singleton
@Subcomponent(modules = {DefaultDispatchModule.class, AppDispatchModule.class})
public interface MVCComponent {
  Response request();
//  Executor executor();

  @Subcomponent.Builder
  interface Builder {
    @BindsInstance Builder withRequest(MuRequest request);
    @BindsInstance Builder withMethod(HttpMethod method);
    @BindsInstance Builder withFormData(@FormData RequestParameters parameters);
    @BindsInstance Builder withQueryParameter(@QueryParameter RequestParameters parameters);
    MVCComponent build();
  }
}