package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface NumericValue<T extends Number> extends Value {

	/**
	 * @return  Returns the number.
	 * @uml.property  name="number" readOnly="true"
	 */
	public T getNumber();
}
