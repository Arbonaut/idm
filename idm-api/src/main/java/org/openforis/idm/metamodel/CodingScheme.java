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
		LIST, PARENT
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

}