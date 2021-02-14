package com.hzh.dagger.http;

public class Cookie {

    private final io.muserver.Cookie rawCookie;

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

    public io.muserver.Cookie getRawCookie() {
        return rawCookie;
    }

    public static CookieBuilder newBuilder() {
        return new CookieBuilder();
    }

    public static class CookieBuilder {

        private final io.muserver.CookieBuilder rawBuilder;

        public CookieBuilder() {
            this.rawBuilder = io.muserver.CookieBuilder.newSecureCookie();
        }

        public CookieBuilder withName(String name) {
            rawBuilder.withName(name);
            return this;
        }

        public CookieBuilder withValue(String value) {
            rawBuilder.withValue(value);
            return this;
        }

        public CookieBuilder withDomain(String domain) {
            rawBuilder.withDomain(domain);
            return this;
        }

        public CookieBuilder withMaxAge(int maxAge) {
            rawBuilder.withMaxAgeInSeconds(maxAge);
            return this;
        }

        public CookieBuilder withPath(String path) {
            rawBuilder.withPath(path);
            return this;
        }

        public CookieBuilder secure(boolean secure) {
            rawBuilder.secure(secure);
            return this;
        }

        public CookieBuilder httpOnly(boolean httpOnly) {
            rawBuilder.httpOnly(httpOnly);
            return this;
        }

        public CookieBuilder withSameSite(String sameSite) {
            rawBuilder.withSameSite(sameSite);
            return this;
        }

        public Cookie build() {
            return new Cookie(rawBuilder.build());
        }
    }


}
