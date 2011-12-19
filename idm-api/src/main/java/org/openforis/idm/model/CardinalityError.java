package org.openforis.idm.model;

import org.openforis.idm.metamodel.Cardinality;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CardinalityError extends RuleFailure {
	enum Reason {
		MIN_COUNT, MAX_COUNT, REQUIRED
	}
	
	Cardinality getCardinality();
	Reason getReason();
}
