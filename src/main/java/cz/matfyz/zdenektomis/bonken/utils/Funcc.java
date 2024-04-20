package cz.matfyz.zdenektomis.bonken.utils;

/**
 * Functional interface for a callable with two parameters and a return value.
 */
@FunctionalInterface
public interface Funcc<IN1, IN2, OUT> {
    OUT call(IN1 param1, IN2 param2);
}

