package org.openforis.idm.model;

import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.validation.TimeValidator;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TimeAttribute extends Attribute<TimeAttributeDefinition, Time> {

	public TimeAttribute(TimeAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Time createValue(String string) {
		return Time.parseTime(string);
	}

	@Override
	public boolean isEmpty() {
		Time t = getValue();
		return t == null || (t.getHour() == null && t.getMinute() == null);
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		TimeValidator validator = new TimeValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}

}
