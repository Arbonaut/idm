package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class StringField extends Field<String> {
	@Override
	public String parseValue(String s) {
		return s == null ? null : String.valueOf(s);
	}
}
