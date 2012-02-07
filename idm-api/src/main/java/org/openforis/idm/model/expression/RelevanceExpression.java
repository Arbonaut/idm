/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;

/**
 * @author M. Togna
 * 
 */
public class RelevanceExpression extends AbstractBooleanExpression {

	protected RelevanceExpression(ModelJXPathCompiledExpression expression, ModelJXPathContext context) {
		super(expression, context, Boolean.TRUE);
	}
}
