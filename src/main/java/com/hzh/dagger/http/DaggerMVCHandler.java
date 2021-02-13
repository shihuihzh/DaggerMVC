package com.hzh.dagger.http;

import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.Mutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class DaggerMVCHandler implements MuHandler  {
    final RequestRouter requestRouter;

    public DaggerMVCHandler(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    @Override
    public boolean handle(MuRequest muRequest, MuResponse muResponse) throws Exception {

        final Result res = requestRouter.dispatch(muRequest, muResponse);
        muResponse.status(res.getStatusCode());
        muResponse.contentType(res.getContentType());
        Object data = res.getData();

        if (data != null) {
            if (res instanceof FileResult) {
                final FileResult result = (FileResult) res;
                muResponse.headers().set("Content-Disposition", "attachment; filename=" + URLEncoder.encode(result.getFileName(), StandardCharsets.UTF_8));
                muResponse.headers().set("Content-Length", ((File) data).length());
                try (FileInputStream fis = new FileInputStream((File) data)) {
                    Mutils.copy(fis, muResponse.outputStream(), 1024);
                }
            } else if (res instanceof StreamResult) {
                try (InputStream in = (InputStream) data) {
                    Mutils.copy(in, muResponse.outputStream(), 1024);
                }
            } else {
                muResponse.write(res.getData().toString());
            }
        }

        return true;
    }
}
