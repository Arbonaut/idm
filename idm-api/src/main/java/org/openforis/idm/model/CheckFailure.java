package org.openforis.idm.model;

import org.openforis.idm.metamodel.Check;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CheckFailure {

	/**
	 * @return  Returns the check.
	 * @uml.property  name="check" readOnly="true"
	 */
	public Check getCheck();

}
