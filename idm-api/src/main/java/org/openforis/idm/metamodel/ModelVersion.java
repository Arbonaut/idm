package org.openforis.idm.metamodel;

import java.util.Date;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelVersion {

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
	 * @return  Returns the date.
	 * @uml.property  name="date"
	 */
	public Date getDate();

	/**
	 * Setter of the property <tt>date</tt>
	 * @param date  The date to set.
	 * @uml.property  name="date"
	 */
	public void setDate(Date date);

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

}
