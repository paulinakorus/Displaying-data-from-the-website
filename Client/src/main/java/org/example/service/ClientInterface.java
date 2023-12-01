package org.example.service;

public interface ClientInterface {
    <R> R get(String path, Class<R> responseType);
}
