package cz.matfyz.zdenektomis.bonken.utils;

public interface Event<T> {
    void addListener(Action<T> listener);
}

