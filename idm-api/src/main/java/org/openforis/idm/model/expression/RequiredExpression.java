/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.JXPathContext;

/**
 * @author M. Togna
 * 
 */
public class RequiredExpression extends AbstractBooleanExpression {

	protected RequiredExpression(String expression, JXPathContext context) {
		super(expression, context,  Boolean.FALSE);
	}

}
