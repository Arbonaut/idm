package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeListRank {
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

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public MultilingualStringMap getLabels();

	/**
	 * Setter of the property <tt>labels</tt>
	 * 
	 * @param labels
	 *            The labels to set.
	 * @uml.property name="labels"
	 */
	public void setLabels(MultilingualStringMap labels);

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public MultilingualStringMap getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * 
	 * @param descriptions
	 *            The descriptions to set.
	 * @uml.property name="descriptions"
	 */
	public void setDescriptions(MultilingualStringMap descriptions);

}