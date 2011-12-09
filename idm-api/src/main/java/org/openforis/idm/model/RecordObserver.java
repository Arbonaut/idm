package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface RecordObserver {
	/**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     the observable object.
     * @param   arg   an argument passed to the <code>notifyObservers</code>
     *                 method.
     */
	// TODO Define arg (add, remove, etc.)  ...is this needed?
    void update(Object... arg);
}
