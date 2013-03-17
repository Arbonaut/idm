package org.openforis.idm;


import java.io.InputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TestSurveyContext;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class AbstractTest {

	protected static Survey survey;
	protected Entity cluster;
	protected Entity household;
	protected Record record;

	@BeforeClass
	public static void setUp() throws Exception {
		InputStream is = AbstractTest.class.getClassLoader().getResourceAsStream("test.idm.xml");
		SurveyContext surveyContext = new TestSurveyContext();
		SurveyIdmlBinder parser = new SurveyIdmlBinder(surveyContext);
		survey = parser.unmarshal(is);
	}

	@Before
	public void createCluster() {
		this.record = new Record(survey, "2.0");
		this.cluster = record.createRootEntity("cluster");
		Record record2 = new Record(survey, "2.0");
		this.household = record2.createRootEntity("household");
	}
}
