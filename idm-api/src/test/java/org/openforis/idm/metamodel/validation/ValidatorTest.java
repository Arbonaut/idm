package org.openforis.idm.metamodel.validation;

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
import org.openforis.idm.model.TestRecordContext;

/**
 * @author G. Miceli
 */
public class ValidatorTest {
	protected static Survey survey;
	protected Entity cluster;

	@BeforeClass
	public static void setUp() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext();
		SurveyUnmarshaller su = idmlBindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}


	@Before
	public void createCluster() {
		Record record = new Record(new TestRecordContext(), survey, "2.0");
		this.cluster = record.createRootEntity("cluster");
	}
}
