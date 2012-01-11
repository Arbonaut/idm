package org.openforis.idm.metamodel;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
public interface NodeDefinitionVisitor {

	void visit(NodeDefinition definition);

}
