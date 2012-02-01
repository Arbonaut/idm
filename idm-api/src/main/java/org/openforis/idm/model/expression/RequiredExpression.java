/**
 * 
 */
package org.openforis.idm.model.expression;

/**
 * @author M. Togna
 * 
 */
public class RequiredExpression extends AbstractBooleanExpression {

	public RequiredExpression(String expression) {
		super(expression, Boolean.FALSE);
	}

}
