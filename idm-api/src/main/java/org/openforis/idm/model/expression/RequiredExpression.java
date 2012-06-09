/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class RequiredExpression extends AbstractBooleanExpression {

	RequiredExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context, false);
	}
	
	public boolean evaluate(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		return super.evaluate(contextNode, thisNode);
	}
}
