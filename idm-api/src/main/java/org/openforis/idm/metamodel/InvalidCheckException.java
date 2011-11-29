package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvalidCheckException extends Exception {

	private static final long serialVersionUID = 1L;

	private ExplicitCheck explicitCheck;
	
	public InvalidCheckException(ExplicitCheck explicitCheck, String message) {
		super(message);
	}
	
	public ExplicitCheck getCheck() {
		return explicitCheck;
	}
}
