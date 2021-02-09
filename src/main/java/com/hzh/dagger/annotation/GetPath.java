package com.hzh.dagger.annotation;

import dagger.MapKey;

@MapKey(unwrapValue = false)
public @interface GetPath {
  String value();
}