package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Taxon extends Value {

	/**
	 * @return Returns the code.
	 * @uml.property name="code" readOnly="true"
	 */
	public String getCode();

	/**
	 * @return Returns the scientificName.
	 * @uml.property name="scientificName" readOnly="true"
	 */
	public String getScientificName();

	/**
	 * @return Returns the vernacularName.
	 * @uml.property name="vernacularName" readOnly="true"
	 */
	public String getVernacularName();

	/**
	 * @return Returns the ISO3 languageCode.
	 * @uml.property name="languageCode" readOnly="true"
	 */
	public String getLanguageCode();

	/**
	 * @return Returns the languageVariant.
	 * @uml.property name="languageVariant" readOnly="true"
	 */
	public String getLanguageVariant();
}
