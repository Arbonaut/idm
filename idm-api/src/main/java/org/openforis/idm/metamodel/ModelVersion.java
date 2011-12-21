package org.openforis.idm.metamodel;

import java.util.Date;
import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelVersion {

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * @return Returns the date.
	 * @uml.property name="date"
	 */
	public Date getDate();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();
	
	/**
	 * @return Returns the position.
	 * @uml.property name="position"
	 */
	public int getPosition();

}
