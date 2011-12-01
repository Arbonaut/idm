package org.openforis.idm.model;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Code<T> extends Value {
	/**
	 * @return Returns the code.
	 * @uml.property name="code" readOnly="true"
	 */
	public T getCode();

	/**
	 * @return Returns the qualifier.
	 * @uml.property name="qualifier" readOnly="true"
	 */
	public String getQualifier();

}
