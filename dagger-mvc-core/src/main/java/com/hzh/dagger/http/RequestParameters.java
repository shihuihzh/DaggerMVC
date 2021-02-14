package com.hzh.dagger.http;

import java.util.List;
import java.util.Map;

public class RequestParameters {
    public io.muserver.RequestParameters rawRequestParameters;

    public RequestParameters(io.muserver.RequestParameters rawRequestParameters) {
        this.rawRequestParameters = rawRequestParameters;
    }

    public io.muserver.RequestParameters getRawRequestParameters() {
        return rawRequestParameters;
    }

    public String get(String name) {
        return rawRequestParameters.get(name);
    }

    public List<String> getAll(String name) {
        return rawRequestParameters.getAll(name);
    }

    public boolean contanis(String name) {
        return rawRequestParameters.contains(name);
    }

    public Map<String, List<String>> all() {
        return rawRequestParameters.all();
    }

    @Override
    public String toString() {
        return rawRequestParameters.toString();
    }
}
