package org.openforis.idm.model;

import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Date implements Value {

	private static final String DELIM = "-";
	private final Integer day;
	private final Integer month;
	private final Integer year;
	
	public Date(Integer year, Integer month, Integer day) {
		this.year = year;
		this.month = month;
		this.day = day;		
	}

	public static Date parseDate(String string){
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			StringTokenizer st = new StringTokenizer(string, DELIM);
			int tokens = st.countTokens();
			if(tokens != 3){
				throw new IllegalArgumentException("Invalid date " + string);
			}
			int year =  Integer.parseInt(st.nextToken());
			int month =  Integer.parseInt(st.nextToken());
			int day =  Integer.parseInt(st.nextToken());
			return new Date(year, month, day);
		}
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
	
	public java.util.Date toJavaDate() {
		Calendar cal = toCalendar();
		return cal == null ? null: cal.getTime();
	}

	public String toXmlDate() {
		Formatter formatter = new Formatter();
		formatter.format("%04d-%02d-%02d", year, month, day);
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("year", year)
			.append("month", month)
			.append("day", day)
			.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
}
