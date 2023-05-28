package com.bonken.utils;

public interface Event<T> {
    void addListener(Action<T> listener);
}

