package com.hzh;

import com.hzh.app.DaggerAppComponent;
import com.hzh.http.RequestRouter;
import com.hzh.http.DaggerMVCHandler;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunLocal {
    private static final Logger logger = LoggerFactory.getLogger(RunLocal.class);
    public static void main(String[] args) {
        final RequestRouter requestRouter = DaggerAppComponent.builder().build().requestRouter();

        MuServer server = MuServerBuilder.httpServer()
                .withHttpPort(8080)
                .addHandler(new DaggerMVCHandler(requestRouter))
                .start();
        logger.info("Started server at " + server.uri());

    }
}
