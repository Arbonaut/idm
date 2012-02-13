/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class CheckExpression extends AbstractBooleanExpression {

	CheckExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context, true);
	}

	public boolean evaluate(Node<?> node) throws InvalidPathException {
		Entity parent = node.getParent();
		return evaluate(parent, node);
	}
}
