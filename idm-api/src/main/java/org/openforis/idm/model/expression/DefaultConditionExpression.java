/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * if="" expression on check in idm
 * 
 * @author M. Togna
 * @author G. Miceli
 */
public class DefaultConditionExpression extends AbstractBooleanExpression {

	protected DefaultConditionExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context, false);
	}

	public boolean evaluate(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		return evaluate(contextNode, thisNode);
	}
	
}
