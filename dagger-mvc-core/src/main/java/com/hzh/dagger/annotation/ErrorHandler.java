package com.hzh.dagger.annotation;

import dagger.MapKey;

@MapKey
public @interface ErrorHandler {
  int value();
}