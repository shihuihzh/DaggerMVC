package com.hzh.dagger;

import javax.inject.Inject;

public class Url {
    private final String path;

    @Inject
    public Url(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
