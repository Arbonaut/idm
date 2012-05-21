package org.openforis.idm.model;

/**
 * 
 * @author G. Miceli
 *
 */
public final class BooleanValue implements Value {

	private boolean value;
	
	public BooleanValue(Boolean value) {
		this.value = value;
	}
	
	public Boolean getValue() {
		return value;
	}
}
