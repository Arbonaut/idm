/**
 * 
 */
package org.openforis.idm.model.expression;

/**
 * (if expression in idm)
 * 
 * @author M. Togna
 * 
 */
public class ConditionalExpression extends AbstractBooleanExpression {

	public ConditionalExpression(String expression) {
		super(expression, Boolean.FALSE);
	}

}
