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
public class RelevanceExpression extends AbstractBooleanExpression {

	RelevanceExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context, true);
	}
	
	public boolean evaluate(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		return super.evaluate(contextNode, thisNode);
	}
}
