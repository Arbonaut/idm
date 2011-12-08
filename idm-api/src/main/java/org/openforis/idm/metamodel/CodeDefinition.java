package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeDefinition {

	/**
	 * @return Returns the code.
	 * @uml.property name="code" readOnly="true"
	 */
	public String getCode();

	/**
	 * @return Returns the codingScheme.
	 * @uml.property name="codingScheme" readOnly="true"
	 */
	public CodingScheme getCodingScheme();


}
