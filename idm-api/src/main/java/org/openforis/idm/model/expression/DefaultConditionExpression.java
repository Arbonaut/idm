/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
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

	public boolean evaluate(Attribute<?,?> node) throws InvalidPathException {
		Entity parent = node.getParent();
		return evaluate(parent, null);
	}
}
