package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeDefinition {

	/**
	 * @return Returns the code.
	 * @uml.property name="code"
	 */
	public String getCode();

	/**
	 * Setter of the property <tt>code</tt>
	 * 
	 * @param code
	 *            The code to set.
	 * @uml.property name="code"
	 */
	public void setCode(String code);

	/**
	 * @return Returns the codingScheme.
	 * @uml.property name="codingScheme"
	 */
	public CodingScheme getCodingScheme();

	/**
	 * Setter of the property <tt>codingScheme</tt>
	 * 
	 * @param codingScheme
	 *            The codingScheme to set.
	 * @uml.property name="codingScheme"
	 */
	public void setCodingScheme(CodingScheme codingScheme);

}
