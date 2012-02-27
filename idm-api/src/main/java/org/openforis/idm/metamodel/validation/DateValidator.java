package org.openforis.idm.metamodel.validation;

import java.util.Calendar;

import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
public class DateValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		try {
			DateAttribute attr = (DateAttribute) nodeState.getNode();
			Date date = attr .getValue();
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
