/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author M. Togna
 * 
 */
public final class ValidationContext {

	private ExpressionFactory expressionFactory;

	public ValidationContext() {
	}

	public ExpressionFactory getExpressionFactory() {
		if(expressionFactory == null){
			expressionFactory = new ExpressionFactory();
		}
		return expressionFactory;
	}

	public void setExpressionFactory(ExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}
	
}
