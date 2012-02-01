/**
 * 
 */
package org.openforis.idm.model.expression;

/**
 * @author M. Togna
 * 
 */
public class InvalidPathException extends Exception {

	private static final long serialVersionUID = 1L;

	protected InvalidPathException() {
		super();
	}

	protected InvalidPathException(String message, Throwable cause) {
		super(message, cause);
	}

	protected InvalidPathException(String message) {
		super(message);
	}

	protected InvalidPathException(Throwable cause) {
		super(cause);
	}

}
