package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealAttribute extends NumberAttribute<Double, RealValue> {

	private static final long serialVersionUID = 1L;

	public RealAttribute(NumberAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealAttribute with integer NumberDefinition");
		}
	}

	@Override
	public RealValue getValue() {
		Double value = (Double) getField(0).getValue(); 
		Survey survey = getSurvey();
		String unitName = (String) getField(1).getValue();
		Unit unit = survey.getUnit(unitName);
		return new RealValue(value, unit);
	}

	public void setValue(Double value) {
		super.setValue(new RealValue(value));
	}
}
