package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface NumericRange<T extends Number> extends Value {

	/**
	 * @return Returns the from.
	 * @uml.property name="from" readOnly="true"
	 */
	public T getFrom();

	/**
	 * @return Returns the to.
	 * @uml.property name="to" readOnly="true"
	 */
	public T getTo();
}
