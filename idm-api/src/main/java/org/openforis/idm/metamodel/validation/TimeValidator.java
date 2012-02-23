/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author M. Togna
 * 
 */
public class TimeValidator implements ValidationRule<TimeAttribute> {

	@Override
	public boolean evaluate(TimeAttribute node) {
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
