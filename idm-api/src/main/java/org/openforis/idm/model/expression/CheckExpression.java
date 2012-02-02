/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.JXPathContext;

/**
 * @author M. Togna
 * 
 */
public class CheckExpression extends AbstractBooleanExpression {

	protected CheckExpression(String expression, JXPathContext context) {
		super(expression, context, Boolean.TRUE);
	}

}
