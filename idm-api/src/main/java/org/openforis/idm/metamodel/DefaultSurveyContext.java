package org.openforis.idm.metamodel;

import org.openforis.idm.metamodel.ExternalCodeListProvider;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.validation.LookupProvider;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * 
 * @author gino
 *
 */
public class DefaultSurveyContext implements SurveyContext {

	private ExpressionFactory expressionFactory;

	public DefaultSurveyContext() {
		expressionFactory = new ExpressionFactory();
		LookupProvider lookupProvider = new UnspecifiedLookupProvider();
		expressionFactory.setLookupProvider(lookupProvider);
	}

	@Override
	public ExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	@Override
	public Validator getValidator() {
		return new Validator();
	}

	@Override
	public ExternalCodeListProvider getExternalCodeListProvider() {
		throw new UnsupportedOperationException();
	}
	
	public class UnspecifiedLookupProvider implements LookupProvider {
		@Override
		public Object lookup(String name, String attribute, Object... keys) {
			throw new UnsupportedOperationException();
		}
	}
}
