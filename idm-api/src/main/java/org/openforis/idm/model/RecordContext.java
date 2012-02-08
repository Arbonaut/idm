/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author M. Togna
 * 
 */
public final class RecordContext {

	private ExpressionFactory expressionFactory;

	public RecordContext() {
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
