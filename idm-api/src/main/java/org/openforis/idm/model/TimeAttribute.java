package org.openforis.idm.model;

import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TimeAttribute extends Attribute<TimeAttributeDefinition, Time> {

	private static final long serialVersionUID = 1L;

	public TimeAttribute(TimeAttributeDefinition definition) {
		super(definition, Integer.class, Integer.class);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Integer> getHourField() {
		return (AttributeField<Integer>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Integer> getMinuteField() {
		return (AttributeField<Integer>) getField(1);
	}
	
	@Override
	public Time getValue() {
		Integer hour = getHourField().getValue();
		Integer minute = getMinuteField().getValue();
		return new Time(hour, minute);
	}
	
	@Override
	public void setValue(Time time) {
		if ( time == null ) {
			clearValue();
		} else {
			Integer hour = time.getHour();
			Integer minute = time.getMinute();
			getHourField().setValue(hour);
			getMinuteField().setValue(minute);
		}
	}

}
