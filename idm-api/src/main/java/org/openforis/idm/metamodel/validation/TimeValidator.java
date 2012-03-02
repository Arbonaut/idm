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
	public boolean evaluate(TimeAttribute timeAttribute) {
		try {
			Time time = timeAttribute.getValue();
			Integer hour = time.getHour();
			Integer minute = time.getMinute();
			if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
