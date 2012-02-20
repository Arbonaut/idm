package org.openforis.idm.model;

import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.metamodel.validation.TimeValidator;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TimeAttribute extends Attribute<TimeAttributeDefinition, Time> {

	public TimeAttribute(TimeAttributeDefinition definition) {
		super(definition, 2);
	}

	@SuppressWarnings("unchecked")
	public Field<Integer> getHourField() {
		return (Field<Integer>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<Integer> getMinuteField() {
		return (Field<Integer>) getField(1);
	}
	
	@Override
	public Time getValue() {
		Integer hour = getHourField().getValue();
		Integer minute = getMinuteField().getValue();
		return new Time(hour, minute);
	}
	
	@Override
	public void setValue(Time time) {
		Integer hour = time.getHour();
		Integer minute = time.getMinute();
		getHourField().setValue(hour);
		getMinuteField().setValue(minute);
	}
	
	@Override
	public Time createValue(String string) {
		return Time.parseTime(string);
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		TimeValidator validator = new TimeValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}

}
