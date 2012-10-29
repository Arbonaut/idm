package org.openforis.idm.metamodel.xml;

import javax.xml.bind.ValidationEvent;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvalidIdmlException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private ValidationEvent[] validationEvents;

	public InvalidIdmlException(String message) {
		super(message);
	}
	
	public InvalidIdmlException(ValidationEvent[] validationEvents) {
		this.validationEvents = validationEvents;
	}
	
	public ValidationEvent[] getValidationEvents() {
		return validationEvents;
	}
	
	@Override
	public String getMessage() {
		if ( validationEvents != null && validationEvents.length > 0 ) {
			StringBuilder sb = new StringBuilder();
			sb.append("Invalid IDML. Messages: ");
			for (int i = 0; i < validationEvents.length; i++) {
				ValidationEvent event = validationEvents[i];
				if ( i > 0 ) {
					sb.append(", ");
				}
				sb.append(event.getMessage());
			}
			return sb.toString();
		} else {
			return super.getMessage();
		}
	}
}
