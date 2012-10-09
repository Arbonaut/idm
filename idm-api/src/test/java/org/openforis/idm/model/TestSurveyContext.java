package org.openforis.idm.model;

import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.model.expression.ExpressionFactory;

/**
 * @author G. Miceli
 */
public class TestSurveyContext extends DefaultSurveyContext {
	public TestSurveyContext() {
		super();
		ExpressionFactory expressionFactory = getExpressionFactory();
		TestLookupProvider testLookupProvider = new TestLookupProvider();
		expressionFactory.setLookupProvider(testLookupProvider);
	}
}
