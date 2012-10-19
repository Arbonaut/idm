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
abstract class AbstractBooleanExpression extends AbstractExpression {

	private boolean defaultValue;

	AbstractBooleanExpression(ModelJXPathCompiledExpression compiledExpression, ModelJXPathContext jxPathContext, boolean defaultValue) {
		super(compiledExpression, jxPathContext);
		this.defaultValue = defaultValue;
	}

	protected boolean evaluate(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		try {
			Object result = evaluateSingle(contextNode, thisNode);
			if (result == null) {
				return false;
			} else if (result instanceof Boolean) {
				return (Boolean) result;
			} else {
				// result is not null
				return true;
			}
		} catch (MissingValueException e) {
			return defaultValue;
		}
	}
}
