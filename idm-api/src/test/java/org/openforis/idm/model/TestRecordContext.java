package org.openforis.idm.model;

import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.validation.ExternalLookupProvider;

public class TestRecordContext implements RecordContext {

	private ExpressionFactory expressionFactory;

	public TestRecordContext() {
		expressionFactory = new ExpressionFactory();
		ExternalLookupProvider externalLookupProvider = new TestExternalLookupProviderImpl();
		expressionFactory.setExternalLookupProvider(externalLookupProvider);
	}

	@Override
	public ExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

}
