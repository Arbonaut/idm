package org.openforis.idm.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Date {

	private static final String DELIM = "-";
	private final Integer day;
	private final Integer month;
	private final Integer year;
	
	public Date(Integer year, Integer month, Integer day) {
		this.year = year;
		this.month = month;
		this.day = day;		
	}

	public static Date parseDate(String year, String month, String day) {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		int d = Integer.parseInt(day);
		return new Date(y, m, d);
	}
	
	public static Date parseDate(String string){
		StringTokenizer st = new StringTokenizer(string, DELIM);
		int tokens = st.countTokens();
		if(tokens != 3){
			throw new IllegalArgumentException("Invalid date " + string);
		}
		String year = st.nextToken();
		String month = st.nextToken();
		String day = st.nextToken();
		return parseDate(year, month, day);
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
