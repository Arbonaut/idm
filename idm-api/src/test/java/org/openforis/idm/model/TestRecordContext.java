package org.openforis.idm.model;

import org.openforis.idm.metamodel.validation.LookupProvider;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.ExpressionFactory;

public class TestRecordContext implements RecordContext {

	private ExpressionFactory expressionFactory;

	public TestRecordContext() {
		expressionFactory = new ExpressionFactory();
		LookupProvider lookupProvider = new TestLookupProviderImpl();
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

}
