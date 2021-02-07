package com.hzh.dagger;

import dagger.MapKey;

@MapKey
@interface DispatchPath {
  String value();
}