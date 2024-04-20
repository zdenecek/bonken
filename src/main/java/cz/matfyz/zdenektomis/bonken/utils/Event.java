package cz.matfyz.zdenektomis.bonken.utils;


/**
 * Interface for an event that can be listened to.
 * @param <T> Type of the parameter of the event
 */
public interface Event<T> {

    /**
     * @param listener Listener to be added
     */
    void addListener(Action<T> listener);
}

