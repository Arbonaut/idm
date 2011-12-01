package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface DateValue extends TimestampValue {

	/**
	 * @return Returns the day.
	 * @uml.property name="day" readOnly="true"
	 */
	public Integer getDay();

	/**
	 * @return Returns the month.
	 * @uml.property name="month" readOnly="true"
	 */
	public Integer getMonth();

	/**
	 * @return Returns the year.
	 * @uml.property name="year" readOnly="true"
	 */
	public Integer getYear();
}
