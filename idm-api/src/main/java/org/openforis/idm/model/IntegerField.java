package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class IntegerField extends Field<Integer> {
	@Override
	public Integer parseValue(String s) {
		return s == null ? null : Integer.valueOf(s);
	}
}
