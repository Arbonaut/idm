/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
public class CheckExpression extends AbstractBooleanExpression {

	protected CheckExpression(String expression, ModelJXPathContext context) {
		super(expression, context, Boolean.TRUE);
	}

}
