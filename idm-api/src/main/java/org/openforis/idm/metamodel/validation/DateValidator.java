package org.openforis.idm.metamodel.validation;

import java.util.Calendar;

import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
public class DateValidator implements Validator<DateAttribute> {

	@Override
	public boolean validate(DateAttribute attr) {
		try {
			Date date = attr.getValue();
			Calendar cal = date.toCalendar();
			if(cal == null) {
				return false;
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
