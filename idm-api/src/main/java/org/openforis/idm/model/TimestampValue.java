package org.openforis.idm.model;

import java.util.Calendar;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface TimestampValue extends Value {

	public Calendar toCalendar();

}
