package org.openforis.idm.model;

import org.openforis.idm.metamodel.Unit;

/**
 * 
 * @author G. Miceli
 *
 */
public class IntegerValue extends NumberValue<Integer> {

	public IntegerValue(Integer value, Unit unit) {
		super(value, unit);
	}

	public IntegerValue(Integer value) {
		super(value, null);
	}
}
