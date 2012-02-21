package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class DoubleField extends Field<Double> {
	@Override
	public Double parseValue(String s) {
		return s == null ? null : Double.valueOf(s);
	}
}
