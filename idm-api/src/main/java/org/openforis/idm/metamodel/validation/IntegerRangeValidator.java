/**
 * 
 */
package org.openforis.idm.metamodel.validation;


import org.openforis.idm.model.IntegerRange;
import org.openforis.idm.model.IntegerRangeAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class IntegerRangeValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		IntegerRangeAttribute node = (IntegerRangeAttribute) nodeState.getNode();
		IntegerRange range = node.getValue();
		Integer from = range.getFrom();
		Integer to = range.getTo();
		return to >= from;
	}

}
