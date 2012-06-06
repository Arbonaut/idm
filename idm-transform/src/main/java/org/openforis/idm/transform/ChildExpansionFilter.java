package org.openforis.idm.transform;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 *
 */
public interface ChildExpansionFilter {
	
	boolean isIncluded(NodeDefinition nodeDefinition);
}
