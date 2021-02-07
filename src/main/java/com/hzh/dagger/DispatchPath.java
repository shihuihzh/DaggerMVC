package com.hzh.dagger;

import com.hzh.http.HttpMethod;
import dagger.MapKey;

@MapKey(unwrapValue = false)
public @interface DispatchPath {
  String value();
  HttpMethod method() default HttpMethod.ALL;
}