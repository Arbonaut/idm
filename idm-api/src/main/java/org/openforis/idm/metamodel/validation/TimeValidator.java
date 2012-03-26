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
	public ValidationResultFlag evaluate(TimeAttribute timeAttribute) {
		try {
			Time time = timeAttribute.getValue();
			if ( time != null ) {
				Integer hour = time.getHour();
				Integer minute = time.getMinute();
				
				if ( !( hour == null || minute == null ) ) {
					if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
						return ValidationResultFlag.ERROR;
					}
				}
			}
			return ValidationResultFlag.OK;
		} catch (Exception e) {
			return ValidationResultFlag.ERROR;
		}
	}

}
