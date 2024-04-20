package cz.matfyz.zdenektomis.bonken.utils;

/**
 * Functional interface for a callable with single parameter and a return value.
 */
@FunctionalInterface
public interface Func<IN, OUT> {
    OUT call(IN param);
}

