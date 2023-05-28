package com.bonken.utils;

@FunctionalInterface
public interface Func<IN, OUT>  {
    OUT call(IN param);
}

