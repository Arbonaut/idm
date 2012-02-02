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
		this.expressionFactory = new ExpressionFactory();
	}

	public ExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}
	
	public ExternalLookupProvider getExternalLookupProvider() {
		return getExpressionFactory().getExternalLookupProvider();
	}

	public void setExternalLookupProvider(ExternalLookupProvider externalLookupProvider) {
		getExpressionFactory().setExternalLookupProvider(externalLookupProvider);
	}
	
	
}
