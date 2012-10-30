package org.openforis.idm.transform2;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * Defines behavior when expanding child nodes
 * 
 * @author G. Miceli
 *
 */
public interface ChildExpansionFilter {
	
	/**
	 * Determines whether children are include when expanding
	 * nodes into columns
	 */
	boolean isIncluded(NodeDefinition nodeDefinition);
}
