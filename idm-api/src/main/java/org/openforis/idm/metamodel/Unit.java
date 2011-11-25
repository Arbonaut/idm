package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Unit {

	/**
	 * @return  Returns the labels.
	 * @uml.property  name="labels"
	 */
	public MultilingualStringMap getLabels();

	/**
	 * Setter of the property <tt>labels</tt>
	 * @param labels  The labels to set.
	 * @uml.property  name="labels"
	 */
	public void setLabels(MultilingualStringMap labels);

	/**
	 * @return  Returns the abbreviations.
	 * @uml.property  name="abbreviations"
	 */
	public MultilingualStringMap getAbbreviations();

	/**
	 * Setter of the property <tt>abbreviations</tt>
	 * @param abbreviations  The abbreviations to set.
	 * @uml.property  name="abbreviations"
	 */
	public void setAbbreviations(MultilingualStringMap abbreviations);

	/**
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public String getName();

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name);

	/**
	 * @return  Returns the dimension.
	 * @uml.property  name="dimension"
	 */
	public String getDimension();

	/**
	 * Setter of the property <tt>dimension</tt>
	 * @param dimension  The dimension to set.
	 * @uml.property  name="dimension"
	 */
	public void setDimension(String dimension);

	/**
	 * @return  Returns the conversionFactor.
	 * @uml.property  name="conversionFactor"
	 */
	public Number getConversionFactor();

	/**
	 * Setter of the property <tt>conversionFactor</tt>
	 * @param conversionFactor  The conversionFactor to set.
	 * @uml.property  name="conversionFactor"
	 */
	public void setConversionFactor(Number conversionFactor);

}
