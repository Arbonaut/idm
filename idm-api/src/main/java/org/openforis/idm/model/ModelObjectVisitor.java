package org.openforis.idm.model;

import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author G. Miceli
 */
public interface ModelObjectVisitor {

	void visit(ModelObject<? extends SchemaObjectDefinition> node, int idx);

}
