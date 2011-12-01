package org.openforis.idm.model;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface TextValue extends Value {

	/**
	 * @return Returns the string.
	 * @uml.property name="string" readOnly="true"
	 */
	public String getString();
}
