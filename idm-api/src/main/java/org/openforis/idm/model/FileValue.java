package org.openforis.idm.model;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface FileValue extends Value {

	/**
	 * @return Returns the filename.
	 * @uml.property name="filename" readOnly="true"
	 */
	public String getFilename();

	/**
	 * @return Returns the size.
	 * @uml.property name="size" readOnly="true"
	 */
	public Long getSize();

}
