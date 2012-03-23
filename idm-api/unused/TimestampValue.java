package org.openforis.idm.model;

import java.util.Calendar;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface TimestampValue {

	/**
	 * @return A Calendar representing the time/date, or null if any other the required fields are null.  
	 * Calendars will be returned as non-lenient; if the Calendar contains and invalid date or time, {@link IllegalArgumentException}
	 * will be thrown when accessing Calendar fields.  
	 */
	public Calendar toCalendar();

}
