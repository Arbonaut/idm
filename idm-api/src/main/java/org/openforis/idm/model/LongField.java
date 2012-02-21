package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class LongField extends Field<Long> {
	@Override
	public Long parseValue(String s) {
		return s == null ? null : Long.valueOf(s);
	}
}
