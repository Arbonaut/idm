/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.model.RealRange;
import org.openforis.idm.model.RealRangeAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class RealRangeValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		RealRangeAttribute node = (RealRangeAttribute) nodeState.getNode();
		RealRange range = node.getValue();
		Double from = range.getFrom();
		Double to = range.getTo();
		return to >= from;
	}

}
