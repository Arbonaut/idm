package org.openforis.idm.metamodel.xml;

import javax.xml.bind.ValidationEvent;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvalidIdmlException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private ValidationEvent[] validationEvents;

	public InvalidIdmlException(ValidationEvent[] validationEvents) {
		this.validationEvents = validationEvents;
	}
	
	public ValidationEvent[] getValidationEvents() {
		return validationEvents;
	}
}
