package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodingScheme {
	public enum CodeType {
		NUMERIC, ALPHANUMERIC
	}

	public enum CodeScope {
		SCHEME, LOCAL
	}

	/**
	 * @return Returns the codeType.
	 * @uml.property name="codeType"
	 */
	public CodingScheme.CodeType getCodeType();

	/**
	 * Setter of the property <tt>codeType</tt>
	 * 
	 * @param codeType
	 *            The codeType to set.
	 * @uml.property name="codeType"
	 */
	public void setCodeType(CodingScheme.CodeType codeType);

	/**
	 * @return Returns the codeScope.
	 * @uml.property name="codeScope"
	 */
	public CodingScheme.CodeScope getCodeScope();

	/**
	 * Setter of the property <tt>codeScope</tt>
	 * 
	 * @param codeScope
	 *            The codeScope to set.
	 * @uml.property name="codeScope"
	 */
	public void setCodeScope(CodingScheme.CodeScope codeScope);

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public void setName(String name);

}