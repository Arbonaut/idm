package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface SpatialReferenceSystem {

	/**
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public String getId();

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(String id);


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
	 * @return  Returns the descriptions.
	 * @uml.property  name="descriptions"
	 */
	public MultilingualStringMap getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * @param descriptions  The descriptions to set.
	 * @uml.property  name="descriptions"
	 */
	public void setDescriptions(MultilingualStringMap descriptions);

	/**
	 * @return  Returns the wellKnownText.
	 * @uml.property  name="wellKnownText"
	 */
	public String getWellKnownText();

	/**
	 * Setter of the property <tt>wellKnownText</tt>
	 * @param wellKnownText  The wellKnownText to set.
	 * @uml.property  name="wellKnownText"
	 */
	public void setWellKnownText(String wellKnownText);
	
}
