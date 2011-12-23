package org.openforis.idm.model;

import java.util.Iterator;

import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Expression {

	Object evaluate(Record context);

	Object evaluate(ModelObject<? extends SchemaObjectDefinition> context);

	Iterator<?> Iterate(ModelObject<? extends SchemaObjectDefinition> context);
}
