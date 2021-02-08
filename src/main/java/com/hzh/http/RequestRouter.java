package com.hzh.http;

import com.hzh.dagger.MVCComponent;
import com.hzh.dagger.Response;
import io.muserver.MuRequest;

import javax.inject.Inject;
import javax.inject.Provider;

public class RequestRouter {
  private final Provider<MVCComponent.Builder> mvcComponentProvider;

  @Inject RequestRouter(
      Provider<MVCComponent.Builder> mvcComponentProvider) {
    this.mvcComponentProvider = mvcComponentProvider;
  }

  public Response dispatch(MuRequest muRequest) throws Exception {
    return mvcComponentProvider.get()
            .withRequest(muRequest)
            .withMethod(HttpMethod.of(muRequest.method().toString()))
            .withQueryParameter(muRequest.query())
            .withFormData(muRequest.query())
            .build().request();
  }
}