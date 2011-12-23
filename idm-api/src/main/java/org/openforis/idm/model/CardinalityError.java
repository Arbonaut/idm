package org.openforis.idm.model;

import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CardinalityError extends RuleFailure {
	enum Reason {
		MIN_COUNT, MAX_COUNT, REQUIRED
	}
	
	SchemaObjectDefinition getSchemaObjectDefinition();
	Reason getReason();
}
