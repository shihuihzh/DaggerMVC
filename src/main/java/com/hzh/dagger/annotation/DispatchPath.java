package com.hzh.dagger.annotation;

import com.hzh.dagger.http.HttpMethod;
import dagger.MapKey;

@MapKey(unwrapValue = false)
public @interface DispatchPath {
  String value();
  HttpMethod method() default HttpMethod.ALL;
}