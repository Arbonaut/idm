/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.JXPathContext;

/**
 * @author M. Togna
 * 
 */
public class RelevanceExpression extends AbstractBooleanExpression {

	protected RelevanceExpression(String expression, JXPathContext context) {
		super(expression, context, Boolean.TRUE);
	}

}
