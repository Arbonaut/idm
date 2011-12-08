package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvalidCheckException extends Exception {

	private static final long serialVersionUID = 1L;

	private Check check;

	public InvalidCheckException(Check check, String message) {
		super(message);
	}

	public Check getCheck() {
		return this.check;
	}
}
