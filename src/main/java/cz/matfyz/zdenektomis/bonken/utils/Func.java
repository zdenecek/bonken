package cz.matfyz.zdenektomis.bonken.utils;

@FunctionalInterface
public interface Func<IN, OUT> {
    OUT call(IN param);
}

