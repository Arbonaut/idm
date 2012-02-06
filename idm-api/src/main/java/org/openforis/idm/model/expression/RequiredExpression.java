/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
public class RequiredExpression extends AbstractBooleanExpression {

	protected RequiredExpression(String expression, ModelJXPathContext context) {
		super(expression, context,  Boolean.FALSE);
	}

}
