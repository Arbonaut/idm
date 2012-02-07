/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
abstract class AbstractBooleanExpression extends AbstractExpression {

	private boolean defaultValue;

	protected AbstractBooleanExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context, boolean defaultValue) {
		super(expression, context);
		this.defaultValue = defaultValue;
	}

	public boolean evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		try {
			// if ("true".equals(getNormalizedExpression())) {
			// return Boolean.TRUE;
			// }
			// if ("false".equals(getNormalizedExpression())) {
			// return Boolean.FALSE;
			// }
			Boolean result = (Boolean) super.evaluateSingle(context);
			return result;
		} catch (MissingValueException e) {
			return defaultValue;
		}

	}

}
