/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public interface ValidationRule {

	boolean evaluate(NodeState nodeState);
	
}
