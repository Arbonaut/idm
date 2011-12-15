package org.openforis.idm.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class DateValue implements TimestampValue {

	private Integer day;
	private Integer month;
	private Integer year;
	
	public DateValue(Integer year, Integer month, Integer day) {
		this.year = year;
		this.month = month;
		this.day = day;		
	}

	public Integer getDay() {
		return day;
	}
	
	public Integer getMonth() {
		return month;
	}
	
	public Integer getYear() {
		return year;
	}
	
	@Override
	public Calendar toCalendar() {
		if ( year==null || month==null || day == null ) {
			return null;
		} else {
			GregorianCalendar cal = new GregorianCalendar();
			cal.clear();
			cal.setLenient(false);
			cal.set(year, month-1, day);
			return cal;
		}
	}
}
