package com.hzh.app;

import com.hzh.dagger.MVCModule;
import com.hzh.http.RequestRouter;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {MVCModule.class, AppDispatchModule.class})
public interface AppComponent {
    RequestRouter requestRouter();
}
