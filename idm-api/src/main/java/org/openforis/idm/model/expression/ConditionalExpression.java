/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * (if expression in idm)
 * 
 * @author M. Togna
 * 
 */
public class ConditionalExpression extends AbstractBooleanExpression {

	protected ConditionalExpression(String expression, ModelJXPathContext context) {
		super(expression, context, Boolean.FALSE);
	}

}
