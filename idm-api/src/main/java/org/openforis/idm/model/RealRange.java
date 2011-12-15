package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealRange extends NumericRange<Double> {

	public RealRange(Double value) {
		super(value);
	}

	public RealRange(Double from, Double to) {
		super(from, to);
	}
}
