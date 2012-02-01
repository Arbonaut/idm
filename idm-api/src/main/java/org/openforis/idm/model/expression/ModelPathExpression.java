/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * 
 */
public class ModelPathExpression extends AbstractExpression {

	public ModelPathExpression(String expression) {
		super(expression);
	}

	public Object evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		Object object = evaluateSingle(context);
		return object;
	}

	public List<Node<NodeDefinition>> iterate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		List<Node<NodeDefinition>> list = evaluateMultiple(context);
		return list;
	}

}
