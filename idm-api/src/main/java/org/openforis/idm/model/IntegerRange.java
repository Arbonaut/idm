package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerRange extends NumericRange<Integer> {

	public IntegerRange(Integer value) {
		super(value);
	}

	public IntegerRange(Integer from, Integer to) {
		super(from, to);
	}
}
