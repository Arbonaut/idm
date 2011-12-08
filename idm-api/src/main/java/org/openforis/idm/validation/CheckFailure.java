package org.openforis.idm.validation;

import org.openforis.idm.metamodel.Check;


public interface CheckFailure extends ValidationResult {

	/**
	 * @return  Returns the check.
	 * @uml.property  name="check" readOnly="true"
	 */
	public Check getCheck();

}
