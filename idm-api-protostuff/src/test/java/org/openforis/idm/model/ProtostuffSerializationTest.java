package org.openforis.idm.model;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;

/**
 * 
 * @author G. Miceli
 * 
 */
public class ProtostuffSerializationTest  {

	@Test
	public void testRoundTrip() throws Exception {
		// Set up
		Survey survey = getTestSurvey();
		assignFakeNodeDefinitionIds(survey.getSchema());
		Record record = createTestRecord(survey);
		Entity cluster = record.getRootEntity();
		
		// Write
		ModelSerializer ser = new ModelSerializer(10000);
		byte[] data = ser.toByteArray(cluster);
		
		// Read
		Record record1 = new Record(survey, "2.0");
		Entity cluster1 = record1.createRootEntity("cluster");
		ser.mergeFrom(data, cluster1);

		// Compare
		String r = record1.toString();
		String r1 = record1.toString();
//		System.out.println(r1);
		Assert.assertEquals(r, r1);
	}

	private Survey getTestSurvey() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext(new TestSurveyContext());
		SurveyUnmarshaller su = idmlBindingContext.createSurveyUnmarshaller();
		return su.unmarshal(is);		
	}
	
	private void assignFakeNodeDefinitionIds(Schema schema) {
		Collection<NodeDefinition> defns = schema.getAllDefinitions();
		int defnId = 1;
		for (NodeDefinition defn : defns) {
			defn.setId(defnId++);
		}
	}

	private Record createTestRecord(Survey survey) {
		Record record = new Record(survey, "2.0");
		Entity cluster = record.createRootEntity("cluster");
		addTestValues(cluster, "123_456");
		return record;
	}

	private void addTestValues(Entity cluster, String id) {
		cluster.addValue("id", new Code(id));
		cluster.addValue("gps_realtime", Boolean.TRUE);
		cluster.addValue("region", new Code("001", "aqualiferxxxxxxxxxxxx"));
		cluster.getChildState("region").set(0, true);
		cluster.addValue("district", new Code("002"));
		cluster.addValue("crew_no", 10);
		cluster.addValue("map_sheet", "value 1");
		cluster.addValue("map_sheet", "value 2");
		cluster.addValue("vehicle_location", new Coordinate((double) 12345, (double) 67890, "srs"));
		TextAttribute gpsModel = cluster.addValue("gps_model", "TomTom 1.232");
		gpsModel.getField().getState().set(0,true);
		cluster.setChildState("accessibility", 1);
		
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
			Entity tree1 = plot.addEntity("tree");
			tree1.addValue("tree_no", 1);
			tree1.addValue("dbh", 54.2);
			tree1.addValue("total_height", 2.0);
			// tree1.addValue("bole_height", (Double) null).setMetadata(new
			// CollectAttributeMetadata('*',null,"No value specified"));
			RealAttribute boleHeight = tree1.addValue("bole_height",
					(Double) null);
			boleHeight.getField().setSymbol('B');
			boleHeight.getField().setRemarks("No value specified");
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
}
