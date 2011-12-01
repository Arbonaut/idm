package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface TimeValue extends TimestampValue {

	/**
	 * @return Returns the hour.
	 * @uml.property name="hour" readOnly="true"
	 */
	public Integer getHour();

	/**
	 * @return Returns the minute.
	 * @uml.property name="minute" readOnly="true"
	 */
	public Integer getMinute();

}
