package org.openforis.idm.validation;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface RuleResult {
	
	boolean isPassed();

	Node<?> getNode();
}
