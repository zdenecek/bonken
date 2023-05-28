package com.bonken.utils;

import java.util.ArrayList;

public class SimpleEvent<T> implements Event<T> {

    private ArrayList<Action<T>> listeners = new ArrayList<>();

    public void addListener(Action<T> listener) {
        listeners.add(listener);
    }

    public void fire(T arg) {
        for (Action listener : listeners) {
            listener.call(arg);
        }
    }
}
