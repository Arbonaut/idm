package org.openforis.idm.model;

import org.openforis.idm.metamodel.Check;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CheckFailure extends RuleFailure {
	Check getCheck();
}
