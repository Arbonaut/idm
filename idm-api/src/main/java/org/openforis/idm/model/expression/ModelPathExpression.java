/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
public class ModelPathExpression extends AbstractExpression {

	protected ModelPathExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context);
	}

	public Node<?> evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		List<Node<?>> list = iterate(context);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Node<?>> iterate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		List<Node<?>> list = evaluateMultiple(context);
		return list;
	}

}
