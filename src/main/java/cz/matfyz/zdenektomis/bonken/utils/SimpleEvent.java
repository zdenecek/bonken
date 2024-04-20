package cz.matfyz.zdenektomis.bonken.utils;

import java.util.ArrayList;

/**
 * Simple event implementation.
 */
public class SimpleEvent<T> implements Event<T> {

    private final ArrayList<Action<T>> listeners = new ArrayList<>();

    public void addListener(Action<T> listener) {
        listeners.add(listener);
    }

    /**
     * Fires the event.
     * Calls all listeners with the given argument in the order they were added.
     * @param arg Argument to be passed to the listeners
     */
    public void fire(T arg) {
        for (Action<T> listener : listeners) {
            listener.call(arg);
        }
    }
}
