package com.hzh.dagger.http;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Dispatcher {
    private final String path;
    private final HttpMethod method;
    private final Pattern pathPatten;
    private final List<String> pathToken;

    public Dispatcher(String path, HttpMethod method, Pattern pathPatten, List<String> pathToken) {
        this.path = path;
        this.method = method;
        this.pathPatten = pathPatten;
        this.pathToken = pathToken;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Pattern getPathPatten() {
        return pathPatten;
    }

    public List<String> getPathToken() {
        return pathToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispatcher that = (Dispatcher) o;
        return Objects.equals(path, that.path) && method == that.method && Objects.equals(pathPatten, that.pathPatten) && Objects.equals(pathToken, that.pathToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method, pathPatten, pathToken);
    }

    @Override
    public String toString() {
        return "Dispatcher{" +
                "path='" + path + '\'' +
                ", method=" + method +
                ", pathPatten=" + pathPatten +
                ", pathToken=" + pathToken +
                '}';
    }
}
