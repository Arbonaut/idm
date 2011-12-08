package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface SpatialReferenceSystem {

	/**
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public String getId();

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * @return Returns the wellKnownText.
	 * @uml.property name="wellKnownText"
	 */
	public String getWellKnownText();

}
