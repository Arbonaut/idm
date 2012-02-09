/**
 * 
 */
package org.openforis.idm.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.ConditionalExpression;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.validation.CarinalityResult.Reason;

/**
 * @author M. Togna
 * 
 */
public class CardinalityRule implements Rule<CarinalityResult, Entity> {

	private NodeDefinition nodeDefinition;

	public CardinalityRule(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
	}

	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	@Override
	public CarinalityResult evaluate(Entity node) {
		String name = nodeDefinition.getName();
		if (nodeDefinition.isRequired() || evaluate(nodeDefinition.getRequiredExpression(), node)) {
			List<Node<? extends NodeDefinition>> childNodes = node.getAll(name);
			int validNodes = validNodes(childNodes);
			if (validNodes <= 0) {
				return new CarinalityResult(node, nodeDefinition, Reason.REQUIRED, false);
			}
		} else if (nodeDefinition.getMinCount() != null) {
			List<Node<? extends NodeDefinition>> childNodes = node.getAll(name);
			int validNodes = validNodes(childNodes);
			if (validNodes < nodeDefinition.getMinCount()) {
				return new CarinalityResult(node, nodeDefinition, Reason.MIN_COUNT, false);
			}
		} else if (nodeDefinition.getMaxCount() != null) {
			List<Node<? extends NodeDefinition>> children = node.getAll(name);
			if (children.size() > nodeDefinition.getMaxCount()) {
				return new CarinalityResult(node, nodeDefinition, Reason.MAX_COUNT, false);
			}
		}
		return new CarinalityResult(node, nodeDefinition, null, true);
	}

	private int validNodes(List<Node<? extends NodeDefinition>> nodes) {
		int valid = 0;
		for (Node<? extends NodeDefinition> node : nodes) {
			if (node instanceof Entity) {
				if (((Entity) node).size() > 0) {
					valid++;
				}
			} else if (node instanceof Attribute) {
				if (((Attribute<?, ?>) node).getValue() != null) {
					valid++;
				}
			}
		}
		return valid;
	}

	protected boolean evaluate(String condition, Node<?> context) {
		if (StringUtils.isNotBlank(condition)) {
			ConditionalExpression expression = context.getRecord().getContext().getExpressionFactory().createConditionalExpression(condition);
			try {
				return expression.evaluate(context);
			} catch (InvalidPathException e) {
				throw new RuntimeException("Unable to evaluate expression " + condition, e);
			}
		} else {
			return false;
		}
	}

}
