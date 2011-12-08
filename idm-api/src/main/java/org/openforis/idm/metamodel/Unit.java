package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Unit {

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * @return Returns the abbreviations.
	 * @uml.property name="abbreviations"
	 */
	public List<LanguageSpecificText> getAbbreviations();

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * @return Returns the dimension.
	 * @uml.property name="dimension"
	 */
	public String getDimension();

	/**
	 * @return Returns the conversionFactor.
	 * @uml.property name="conversionFactor"
	 */
	public Number getConversionFactor();

}
