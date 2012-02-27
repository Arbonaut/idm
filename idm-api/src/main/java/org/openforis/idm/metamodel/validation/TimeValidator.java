/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class TimeValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		try {
			TimeAttribute timeAttribute = (TimeAttribute) nodeState.getNode();
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
