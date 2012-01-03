package org.openforis.idm.model;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
public interface ModelObjectVisitor {

	void visit(ModelObject<? extends NodeDefinition> node, int idx);

}
