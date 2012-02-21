package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class BooleanField extends Field<Boolean> {
	@Override
	public Boolean parseValue(String s) {
		return s == null ? null : Boolean.valueOf(s);
	}
}
