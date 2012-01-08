package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class NumericCode extends Code<Integer> {

	public NumericCode(Integer code) {
		super(code);
	}

	public NumericCode(Integer code, String qualifier) {
		super(code, qualifier);
	}
}
