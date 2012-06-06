package org.openforis.idm.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author G. Miceli
 *
 */
public final class BooleanValue implements Value {

	private Boolean value;
	
	public BooleanValue(Boolean value) {
		this.value = value;
	}
	
	public BooleanValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			this.value = null;
		} else {
			this.value = Boolean.parseBoolean(string);
		}
	}

	public Boolean getValue() {
		return value;
	}
}
