package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerAttribute extends NumberAttribute<Integer, IntegerValue> {

	private static final long serialVersionUID = 1L;

	public IntegerAttribute(NumberAttributeDefinition definition) {
		super(definition);
		if ( !definition.isInteger() ) {
			throw new IllegalArgumentException("Attempted to create IntegerAttribute with real NumberDefinition");
		}
	}

	@Override
	public IntegerValue getValue() {
		Integer value = (Integer) getField(0).getValue(); 
		Survey survey = getSurvey();
		String unitName = (String) getField(1).getValue();
		Unit unit = survey.getUnit(unitName);
		return new IntegerValue(value, unit);
	}
	
	public void setValue(Integer value) {
		super.setValue(new IntegerValue(value));
	}
}
