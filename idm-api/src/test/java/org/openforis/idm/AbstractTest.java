package org.openforis.idm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;
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

	@BeforeClass
	public static void setUp() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext(new TestSurveyContext());
		SurveyUnmarshaller su = idmlBindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}

	@Before
	public void createCluster() {
		Record record = new Record(survey, "2.0");
		this.cluster = record.createRootEntity("cluster");
		Record record2 = new Record(survey, "2.0");
		this.household = record2.createRootEntity("household");
	}
}
