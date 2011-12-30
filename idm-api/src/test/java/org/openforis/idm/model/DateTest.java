package org.openforis.idm.model;

import java.util.Calendar;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author G. Miceli
 */
public class DateTest {

	// TODO
	@Test
	public void testInvalidDayToCalendar() {
		Date date = new Date(2011, 12, 33);
		Calendar cal = date.toCalendar();
//		assertEquals(2011, cal.)
		/*
		cal.setLenient(true);
		cal.set(0, 0,0, 0, 0);
		System.out.println(cal.get(Calendar.YEAR));
		*/
	}
}
