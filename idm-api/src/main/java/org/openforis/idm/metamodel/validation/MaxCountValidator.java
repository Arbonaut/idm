/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class MaxCountValidator implements ValidationRule<Entity> {

	private NodeDefinition nodeDefinition;
	
	public MaxCountValidator(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}
	
	@Override
	public boolean evaluate(Entity node) {
		String name = nodeDefinition.getName();
		Integer maxCount = nodeDefinition.getMaxCount();
		if (maxCount == null) {
			return true;
		} else {
			List<Node<?>> children = node.getAll(name);
			return children.size() <= maxCount;
		}
	}
}
