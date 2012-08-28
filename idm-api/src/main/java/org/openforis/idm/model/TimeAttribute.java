package org.openforis.idm.model;

import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TimeAttribute extends Attribute<TimeAttributeDefinition, Time> {

	private static final long serialVersionUID = 1L;

	public TimeAttribute(TimeAttributeDefinition definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	public Field<Integer> getHourField() {
		return (Field<Integer>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<Integer> getMinuteField() {
		return (Field<Integer>) getField(1);
	}
	
	public Integer getHour() {
		return getHourField().getValue();
	}
	
	public void setHour(Integer hour) {
		getHourField().setValue(hour);
	}
	
	public Integer getMinute() {
		return getMinuteField().getValue();
	}
	
	public void setMinute(Integer min) {
		getMinuteField().setValue(min);
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
		onUpdateValue();
	}

}
