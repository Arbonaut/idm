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
public class MinCountValidator implements ValidationRule<Entity> {

	private NodeDefinition nodeDefinition;

	public MinCountValidator(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	@Override
	public ValidationResultFlag evaluate(Entity entity) {
		String childName = nodeDefinition.getName();				
		int minCount = entity.getEffectiveMinCount(childName);
		if ( minCount == 0 ) {
			return ValidationResultFlag.OK;
		} else {
			int nonEmptyCount = 0;
			List<Node<?>> childNodes = entity.getAll(childName);
			for ( Node<?> child : childNodes ) {
				if ( !isEmpty(child) ) {
					nonEmptyCount++;
					if ( nonEmptyCount >= minCount ) {
						return ValidationResultFlag.OK;
					}
				}
			}
			return ValidationResultFlag.ERROR;
		}
	}

	protected boolean isEmpty(Node<?> node){
		return node.isEmpty();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("('");
		sb.append(nodeDefinition.getName());
		sb.append("')");
		return sb.toString();
	}
}
