package org.openforis.idm.validation;

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
		Date date = attr.getValue();
		// TODO Evaluate; implement		
		return false;
	}
}
