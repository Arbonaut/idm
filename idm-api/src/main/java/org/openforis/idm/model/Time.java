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
public final class Time implements Value {
	private static final String DELIM = ":";

	private final Integer hour;
	private final Integer minute;

	public Time(Integer hour, Integer minute) {
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
		return new ToStringBuilder(this)
			.append("hour", hour)
			.append("minute", minute)
			.toString();
	}

	public String toXmlTime() {
		Formatter formatter = new Formatter();
		formatter.format("%02d:%02d:00", hour, minute);
		return formatter.toString();
	}
	
	public static Time parseTime(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			StringTokenizer st = new StringTokenizer(string, DELIM);
			int tokens = st.countTokens();
			// Allow format HH:MM or HH:MM:SS.  Seconds are ignored.
			if (tokens < 2 || tokens > 3) {
				throw new IllegalArgumentException("Invalid time " + string);
			}
			int hour = Integer.parseInt(st.nextToken());
			int minute = Integer.parseInt(st.nextToken());
			return new Time(hour, minute);
		}
	}
	
}
