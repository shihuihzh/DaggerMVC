package com.hzh.dagger.http;

import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.Mutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;


public class DaggerMVCHandler implements MuHandler  {
    final RequestRouter requestRouter;

    public DaggerMVCHandler(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    @Override
    public boolean handle(MuRequest muRequest, MuResponse muResponse) throws Exception {

        final Result res = requestRouter.dispatch(muRequest, muResponse);
        final Cookie[] cookies = res.getCookies();

        muResponse.status(res.getStatusCode());
        muResponse.contentType(res.getContentType());

        if (cookies != null && cookies.length > 0) {
            Arrays.stream(cookies)
                    .map(Cookie::getRawCookie)
                    .forEach(muResponse::addCookie);
        }

        if (res instanceof RedirectResult) {
            muResponse.redirect(((RedirectResult) res).getUrl());
            return true;
        }

        Object data = res.getData();
        if (data != null) {
            if (res instanceof FileResult) {
                final FileResult result = (FileResult) res;
                muResponse.headers().set("Content-Disposition", "attachment; filename=" + Mutils.urlEncode(result.getFileName()));
                muResponse.headers().set("Content-Length", ((File) data).length());
                try (FileInputStream fis = new FileInputStream((File) data)) {
                    Mutils.copy(fis, muResponse.outputStream(), 1024);
                }
            } else if (res instanceof StreamResult) {
                try (InputStream in = (InputStream) data) {
                    Mutils.copy(in, muResponse.outputStream(), 1024);
                }
            } else  {
                muResponse.write(res.getData().toString());
            }
        }

        return true;
    }
}
