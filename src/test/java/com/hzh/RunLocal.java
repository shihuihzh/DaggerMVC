package com.hzh;

import com.hzh.app.DaggerAppComponent;
import com.hzh.dagger.http.MVCHolder;
import com.hzh.dagger.http.RequestRouter;
import com.hzh.dagger.http.DaggerMVCHandler;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.handlers.ResourceHandlerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunLocal {
    private static final Logger logger = LoggerFactory.getLogger(RunLocal.class);
    public static void main(String[] args) {
        final RequestRouter requestRouter = DaggerAppComponent.builder()
                .withHolder(new MVCHolder())
                .build().requestRouter();

        MuServer server = MuServerBuilder.httpServer()
                .withHttpsPort(8080)
                .addHandler(ResourceHandlerBuilder.fileOrClasspath("src/test/resources/web", "/web"))
                .addHandler(new DaggerMVCHandler(requestRouter))
                .start();
        logger.info("Started server at " + server.uri());

    }
}
