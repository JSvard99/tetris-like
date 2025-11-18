package com.dt181g.project.observer;

/**
 * The observable interface used in the observer pattern.
 * @author Johan Svärd
 */
public interface Observable {

    /**
     * Attaches an observer to its observer list.
     * @param observer the observer to be attached.
     */
    void attach(Observer observer);

    /**
     * Detaches an observer from its observer list.
     * @param observer the observer to be detached.
     */
    void detach(Observer observer);

    /**
     * Notify all its observers of some change.
     */
    void notifyObservers();
}
