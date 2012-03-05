/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;

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
	public ValidationResultFlag evaluate(Entity entity) {
		Integer maxCount = nodeDefinition.getMaxCount();
		if (maxCount == null) {
			return ValidationResultFlag.OK;
		} else {
			String childName = nodeDefinition.getName();
			int count = entity.getCount(childName);
			if ( count <= maxCount ) {
				return ValidationResultFlag.OK;
			} else {
				return ValidationResultFlag.ERROR;
			}
		}
	}
}
