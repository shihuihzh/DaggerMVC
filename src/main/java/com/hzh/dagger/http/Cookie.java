package com.hzh.dagger.http;

public class Cookie {

    private io.muserver.Cookie rawCookie;


    public Cookie(io.muserver.Cookie rawCookie) {
        this.rawCookie = rawCookie;
    }

    public String getName() {
        return rawCookie.name();
    }

    public String getValue() {
        return rawCookie.value();
    }

    public String getDomain() {
        return rawCookie.domain();
    }

    public long maxAge() {
        return rawCookie.maxAge();
    }


    public String getPath() {
        return rawCookie.path();
    }

    public boolean isSecure() {
        return rawCookie.isSecure();
    }

    public boolean isHttpOnly() {
        return rawCookie.isHttpOnly();
    }

    public String sameSite() {
        return rawCookie.sameSite();
    }


}
