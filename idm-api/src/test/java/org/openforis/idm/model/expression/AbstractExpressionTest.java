/**
 * 
 */
package org.openforis.idm.model.expression;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.Time;
import org.openforis.idm.validation.ValidationContext;

/**
 * @author M. Togna
 * 
 */
public abstract class AbstractExpressionTest {

	private static final String TEST_IDM = "test.idm.xml";
	private Survey survey;
	private Record record;
	private IdmlBindingContext bindingContext;
	private ValidationContext validationContext;

	@Before
	public void initTest() throws IOException, InvalidIdmlException {
		bindingContext = new IdmlBindingContext();
		survey = unmarshalSurvey();
		record = createRecord();
		validationContext = new ValidationContext();
	}

	private Survey unmarshalSurvey() throws IOException, InvalidIdmlException {
		SurveyUnmarshaller unmarshaller = bindingContext.createSurveyUnmarshaller();
		InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_IDM);
		Survey survey = unmarshaller.unmarshal(is);
		return survey;
	}

	private Record createRecord() {

		Record record = new Record(getSurvey(), "cluster", "2.0");

		Entity cluster = record.getRootEntity();
		String id = "123_456";
		cluster.addValue("id", new Code(id));
		cluster.addValue("gps_realtime", Boolean.TRUE);
		cluster.addValue("region", new Code("001"));
		cluster.addValue("district", new Code("002"));
		cluster.addValue("vehicle_location", new Coordinate(432423423l, 4324324l, "srs"));
		cluster.addValue("gps_model", "TomTom 1.232");
		cluster.addValue("plot_direction", 345.98);
		{
			Entity ts = cluster.addEntity("time_study");
			ts.addValue("date", new Date(2011, 2, 14));
			ts.addValue("start_time", new Time(8, 15));
			ts.addValue("end_time", new Time(15, 29));
		}
		{
			Entity ts = cluster.addEntity("time_study");
			ts.addValue("date", new Date(2011, 2, 15));
			ts.addValue("start_time", new Time(8, 32));
			ts.addValue("end_time", new Time(11, 20));
		}

		{
			Entity plot = cluster.addEntity("plot");
			plot.addValue("no", new Code("1"));
			plot.addValue("share", 80d);
			plot.addValue("subplot", "A");
			Entity tree1 = plot.addEntity("tree");
			tree1.addValue("dbh", 54.2);
			tree1.addValue("total_height", 2.0);
			tree1.addValue("bole_height", (Double) null);
			Entity tree2 = plot.addEntity("tree");
			tree2.addValue("dbh", 82.8);
			tree2.addValue("total_height", 3.0);
		}
		{
			Entity plot = cluster.addEntity("plot");
			plot.addValue("no", new Code("1"));
			plot.addValue("share", 20d);
			plot.addValue("subplot", "B");
		}
		{
			Entity plot = cluster.addEntity("plot");
			plot.addValue("no", new Code("2"));
			Entity tree1 = plot.addEntity("tree");
			tree1.addValue("dbh", 34.2);
			tree1.addValue("total_height", 2.0);
			Entity tree2 = plot.addEntity("tree");
			tree2.addValue("dbh", 85.8);
			tree2.addValue("total_height", 4.0);
		}

		return record;
	}

	protected Survey getSurvey() {
		return survey;
	}

	protected Record getRecord() {
		return record;
	}

	protected ValidationContext getValidationContext() {
		return validationContext;
	}
}
