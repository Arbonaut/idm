/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
public class DefaultValueExpression extends AbstractExpression {

	protected DefaultValueExpression(String expression, ModelJXPathContext context) {
		super(expression, context);
	}

	public Object evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		Object object = super.evaluateSingle(context);
		return object;
	}

}