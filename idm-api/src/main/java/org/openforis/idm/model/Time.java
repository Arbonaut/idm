package org.openforis.idm.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Time {
	private static final String DELIM = ":";

	private final Integer hour;
	private final Integer minute;

	public Time(Integer hour, Integer minute) {
		super();
		this.hour = hour;
		this.minute = minute;
	}

	public Integer getHour() {
		return hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public Calendar toCalendar() {
		if (hour == null || minute == null) {
			return null;
		} else {
			GregorianCalendar cal = new GregorianCalendar();
			cal.clear();
			cal.setLenient(false);
			cal.set(Calendar.HOUR, hour);
			cal.set(Calendar.MINUTE, minute);
			return cal;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hour == null) ? 0 : hour.hashCode());
		result = prime * result + ((minute == null) ? 0 : minute.hashCode());
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
		Time other = (Time) obj;
		if (hour == null) {
			if (other.hour != null)
				return false;
		} else if (!hour.equals(other.hour))
			return false;
		if (minute == null) {
			if (other.minute != null)
				return false;
		} else if (!minute.equals(other.minute))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{hour:").append(hour);
		sb.append(", minute:").append(minute);
		sb.append("}");
		return sb.toString();
	}

	public static Time parseTime(String string) {
		StringTokenizer st = new StringTokenizer(string, DELIM);
		int tokens = st.countTokens();
		if (tokens != 2) {
			throw new IllegalArgumentException("Invalid time " + string);
		}
		String hour = st.nextToken();
		String minute = st.nextToken();
		return parseTime(hour, minute);
	}
	
	public static Time parseTime(String hour, String minute) {
		int h = Integer.parseInt(hour);
		int m = Integer.parseInt(minute);
		return new Time(h, m);
		
	}
}
