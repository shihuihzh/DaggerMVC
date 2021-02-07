package com.hzh.dagger;

import dagger.MapKey;

@MapKey(unwrapValue = false)
public @interface GetPath {
  String value();
}