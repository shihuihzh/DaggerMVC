package com.hzh.app;

import com.hzh.dagger.http.MVCHolder;
import com.hzh.dagger.http.RequestRouter;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, AppDispatchModule.class})
public interface AppComponent {
    RequestRouter requestRouter();

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder withHolder(MVCHolder holder);
        AppComponent build();
    }
}
