package com.hzh.dagger.http;

import io.muserver.MuResponse;

public class Response {
    private MuResponse response;

    public Response(MuResponse response) {
        this.response = response;
    }

    public MuResponse getRawResponse() {
        return this.response;
    }
}
