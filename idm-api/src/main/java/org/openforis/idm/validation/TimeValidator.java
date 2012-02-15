/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author M. Togna
 * 
 */
public class TimeValidator implements Validator<TimeAttribute> {

	@Override
	public boolean validate(TimeAttribute node) {
		try {
			Time time = node.getValue();
			if(time.getHour() < 0 || time.getHour() >=24 || time.getMinute() < 0 || time.getMinute() >=60) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
