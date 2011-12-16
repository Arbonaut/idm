package org.openforis.idm.model;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class DateValueTest {

	@Test
	public void testInvalidDayToCalendar() {
		DateValue date = new DateValue(2011, 12, 33);
		Calendar cal = date.toCalendar();
		
		/*
		cal.setLenient(true);
		cal.set(0, 0,0, 0, 0);
		System.out.println(cal.get(Calendar.YEAR));
		*/
	}
}
