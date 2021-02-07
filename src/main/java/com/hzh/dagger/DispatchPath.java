package com.hzh.dagger;

import dagger.MapKey;

@MapKey
public @interface DispatchPath {
  String value();
}