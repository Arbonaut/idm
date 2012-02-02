/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.JXPathContext;

/**
 * (if expression in idm)
 * 
 * @author M. Togna
 * 
 */
public class ConditionalExpression extends AbstractBooleanExpression {

	protected ConditionalExpression(String expression, JXPathContext context) {
		super(expression, context, Boolean.FALSE);
	}

}
