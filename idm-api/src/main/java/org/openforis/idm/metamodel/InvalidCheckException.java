package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvalidCheckException extends Exception {

	private static final long serialVersionUID = 1L;

	private ValueCheck valueCheck;
	
	public InvalidCheckException(ValueCheck valueCheck, String message) {
		super(message);
	}
	
	public ValueCheck getCheck() {
		return valueCheck;
	}
}
