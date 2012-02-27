/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class MaxCountValidator implements ValidationRule {

	private NodeDefinition nodeDefinition;
	
	public MaxCountValidator(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}
	
	@Override
	public boolean evaluate(NodeState nodeState) {
		Entity entity = (Entity) nodeState.getNode();
		String name = nodeDefinition.getName();
		Integer maxCount = nodeDefinition.getMaxCount();
		if (maxCount == null) {
			return true;
		} else {
			List<Node<?>> children = entity.getAll(name);
			return children.size() <= maxCount;
		}
	}
}
