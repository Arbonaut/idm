/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.metamodel.Check;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class CheckFailure {

	private Check check;

	public CheckFailure(Check check) {
		this.check = check;
	}

	public Check getCheck() {
		return check;
	}
}
