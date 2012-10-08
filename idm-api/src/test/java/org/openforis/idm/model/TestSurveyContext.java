package org.openforis.idm.model;

import org.openforis.idm.metamodel.DefaultSurveyContext;

/**
 * @author G. Miceli
 */
public class TestSurveyContext extends DefaultSurveyContext {
	public TestSurveyContext() {
		super();
		getExpressionFactory().setLookupProvider(new TestLookupProviderImpl());
	}
}
