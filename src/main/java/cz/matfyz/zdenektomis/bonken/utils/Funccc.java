package cz.matfyz.zdenektomis.bonken.utils;

/**
 * Functional interface for a callable with three parameters and a return value.
 */
public interface Funccc<IN1, IN2, IN3, OUT> {
    OUT call(IN1 param1, IN2 param2, IN3 param3);
}
