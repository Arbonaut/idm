package org.openforis.idm.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Date {

	private Integer day;
	private Integer month;
	private Integer year;
	
	public Date(Integer year, Integer month, Integer day) {
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{year:").append(year);
		sb.append(", month:").append(month);
		sb.append(", day:").append(day);
		sb.append("}");
		return sb.toString();
	}
}
