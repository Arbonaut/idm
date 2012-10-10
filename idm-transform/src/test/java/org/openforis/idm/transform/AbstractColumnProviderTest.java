package org.openforis.idm.transform;

import org.junit.BeforeClass;
import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;

public abstract class AbstractColumnProviderTest {
	protected static Survey survey;
	protected static Schema schema;
	
	@BeforeClass
	public static void setUp() {
		SurveyContext ctx = new DefaultSurveyContext();
		survey = ctx.createSurvey();
		schema = survey.getSchema();
	}
}
