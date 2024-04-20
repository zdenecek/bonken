package cz.matfyz.zdenektomis.bonken.utils;

/**
 * Functional interface for a callable with single parameter and no return
 */
@FunctionalInterface
public interface Action<T> {
    void call(T param);
}
