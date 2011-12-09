package org.openforis.idm.model;

import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Expression {

	Object[] evaluate(Record context);

	Object[] evaluate(ModelObject<? extends ModelObjectDefinition> context);
}
