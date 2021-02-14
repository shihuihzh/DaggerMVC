package com.hzh.dagger.http;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PathValueMap implements Map<String, String> {
    Map<String, String> pathValueMap;

    public PathValueMap() {
        pathValueMap = new TreeMap<>();
    }

    @Override
    public int size() {
        return pathValueMap.size();
    }

    @Override
    public boolean isEmpty() {
        return pathValueMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return pathValueMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return pathValueMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return pathValueMap.get(key);
    }

    @Override
    public String put(String key, String value) {
        return pathValueMap.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return pathValueMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        this.pathValueMap.putAll(m);
    }

    @Override
    public void clear() {
        this.pathValueMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.pathValueMap.keySet();
    }

    @Override
    public Collection<String> values() {
        return this.pathValueMap.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return this.pathValueMap.entrySet();
    }

    @Override
    public String toString() {
        return pathValueMap.toString();
    }

    public Map<String, String> toMap() {
        return new TreeMap<>(this.pathValueMap);
    }
}
