package org.openforis.idm.metamodel;

import java.util.List;

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
	 * Setter of the property <tt>default</tt>
	 * 
	 * @param isDefault
	 *            The default to set.
	 * @uml.property name="default"
	 */
	public void setDefault(boolean isDefault);

	/**
	 * @return Returns the default.
	 * @uml.property name="default"
	 */
	public boolean isDefault();

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public void setName(String name);

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * 
	 * @param descriptions
	 *            The descriptions to set.
	 * @uml.property name="descriptions"
	 */
	public void setDescriptions(List<LanguageSpecificText> descriptions);

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * Setter of the property <tt>labels</tt>
	 * 
	 * @param labels
	 *            The descriptions to set.
	 * @uml.property name="labels"
	 */
	public void setLabels(List<LanguageSpecificText> labels);

}