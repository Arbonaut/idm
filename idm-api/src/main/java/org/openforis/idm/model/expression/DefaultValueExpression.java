/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.MissingValueException;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class DefaultValueExpression extends AbstractExpression {

	DefaultValueExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context);
	}

	public Object evaluate(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		try {
			return evaluateSingle(contextNode, thisNode);
		} catch (MissingValueException e) {
			return null;
		}
	}
}
