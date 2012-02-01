/**
 * 
 */
package org.openforis.idm.model.expression;

/**
 * @author M. Togna
 *
 */
public class CheckExpression extends AbstractBooleanExpression {

	public CheckExpression(String expression) {
		super(expression, Boolean.TRUE);
	}

}
