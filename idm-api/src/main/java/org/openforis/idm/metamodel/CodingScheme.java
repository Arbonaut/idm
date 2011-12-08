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
	 * @return Returns the codeScope.
	 * @uml.property name="codeScope"
	 */
	public CodingScheme.CodeScope getCodeScope();

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * @return Returns the default.
	 * @uml.property name="default"
	 */
	public boolean isDefault();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

}