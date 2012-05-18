

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.RealAttribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.Time;
import org.openforis.idm.transform.DateColumnProvider;
import org.openforis.idm.transform.EntityColumnProvider;

/**
 * @author G. Miceli
 */
public class ColumnProviderTest {

	@Test
	public void test() throws IOException, InvalidIdmlException {
		Survey survey = loadModel();
		Record record = new Record(survey, "1.0");
		Entity cluster = record.createRootEntity("cluster");
		addTestValues(cluster);
//		System.out.println(cluster);
		Entity plot = (Entity) cluster.get("plot", 0);
		EntityColumnProvider ecpc = new EntityColumnProvider(plot.getDefinition(), null);
		System.out.println(ecpc.getColumns());
		System.out.println(ecpc.getCells(cluster));
		EntityColumnProvider tsecp = (EntityColumnProvider) ecpc.getProviders().get(2);
		DateColumnProvider dcp = (DateColumnProvider) tsecp.getProviders().get(0);
		dcp.setCollapse(false);
		System.out.println(ecpc.getColumns());
		System.out.println(ecpc.getCells(cluster));
	}

	private void addTestValues(Entity cluster) {
		cluster.addValue("id", new Code("123_456"));
		cluster.addValue("gps_realtime", Boolean.TRUE);
		cluster.addValue("region", new Code("001"));
		cluster.addValue("district", new Code("XXX"));	
		cluster.addValue("crew_no", 10);
		cluster.addValue("map_sheet", "value 1");
		cluster.addValue("map_sheet", "value 2");
		cluster.addValue("vehicle_location", new Coordinate((double)432423423l, (double)4324324l, "srs"));
		cluster.addValue("gps_model", "TomTom 1.232");
		cluster.setChildState("accessibility", 1);
		{
			Entity ts = cluster.addEntity("time_study");
			ts.addValue("date", new Date(2011,2,14));
			ts.addValue("start_time", new Time(8,15));
			ts.addValue("end_time", new Time(15,29));
		}
		{
			Entity ts = cluster.addEntity("time_study");
			ts.addValue("date", new Date(2011,2,15));
			ts.addValue("start_time", new Time(8,32));
			ts.addValue("end_time", new Time(11,20));
		}
		{
			Entity plot = cluster.addEntity("plot");
			plot.addValue("no", new Code("1"));
			plot.addValue("subplot", "A");
			{
				Entity ts = plot.addEntity("time_study");
				ts.addValue("date", new Date(2011,2,15));
				ts.addValue("start_time", new Time(8,32));
				ts.addValue("end_time", new Time(11,20));
			}
			Entity tree1 = plot.addEntity("tree");
			tree1.addValue("tree_no", 1);
			tree1.addValue("dbh", 54.2);
			tree1.addValue("total_height", 2.0);
//			tree1.addValue("bole_height", (Double) null).setMetadata(new CollectAttributeMetadata('*',null,"No value specified"));
			RealAttribute boleHeight = tree1.addValue("bole_height", (Double) null);
			boleHeight.getField(0).setSymbol('*');
			boleHeight.getField(0).setRemarks("No value specified");
			Entity tree2 = plot.addEntity("tree");
			tree2.addValue("tree_no", 2);
			tree2.addValue("dbh", 82.8);
			tree2.addValue("total_height", 3.0);
		}
		{
			Entity plot = cluster.addEntity("plot");
			plot.addValue("no", new Code("2"));
			Entity tree1 = plot.addEntity("tree");
			tree1.addValue("tree_no", 1);
			tree1.addValue("dbh", 34.2);
			tree1.addValue("total_height", 2.0);
			Entity tree2 = plot.addEntity("tree");
			tree2.addValue("tree_no", 2);
			tree2.addValue("dbh", 85.8);
			tree2.addValue("total_height", 4.0);
		}
	}
	
	private Survey loadModel() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext();
		SurveyUnmarshaller surveyUnmarshaller = idmlBindingContext.createSurveyUnmarshaller();
		Survey survey = surveyUnmarshaller.unmarshal(is);
		survey.setName("archenland1");
		return survey;
	}
}
