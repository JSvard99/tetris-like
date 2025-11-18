package com.dt181g.project.observer;

/**
 * The Observer class used in the observer pattern.
 * @author Johan Svärd
 */
public interface Observer {

    /**
     * Used when some change has happened in the observable class that this class obserrves.
     */
    void update();
}
