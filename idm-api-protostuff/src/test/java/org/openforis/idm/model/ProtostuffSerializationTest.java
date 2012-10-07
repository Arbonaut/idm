package org.openforis.idm.model;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.IdmlValidator;

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
		//assignFakeNodeDefinitionIds(survey.getSchema());
		Record record1 = createTestRecord(survey);
		Entity cluster1 = record1.getRootEntity();
		
		// Write
		ModelSerializer ser = new ModelSerializer(10000);
		byte[] data = ser.toByteArray(cluster1);
		
		// Read
		Record record2 = new Record(survey, "2.0");
		Entity cluster2 = record2.createRootEntity("cluster");
		ser.mergeFrom(data, cluster2);

		// Compare
		Assert.assertTrue(record1.getRootEntity().equals(record2.getRootEntity()));
	}

	private Survey getTestSurvey() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext(new TestSurveyContext());
		IdmlValidator su = idmlBindingContext.createSurveyUnmarshaller();
		return su.unmarshal(is);		
	}
	
	private Record createTestRecord(Survey survey) {
		Record record = new Record(survey, "2.0");
		Entity cluster = record.createRootEntity("cluster");
		addTestValues(cluster, "123_456");
		return record;
	}

	private void addTestValues(Entity cluster, String id) {
		EntityBuilder.addValue(cluster, "id", new Code(id));
		EntityBuilder.addValue(cluster, "gps_realtime", Boolean.TRUE);
		EntityBuilder.addValue(cluster, "region", new Code("001", "aqualiferxxxxxxxxxxxx"));
		cluster.getChildState("region").set(0, true);
		EntityBuilder.addValue(cluster, "district", new Code("002"));
		EntityBuilder.addValue(cluster, "crew_no", 10);
		EntityBuilder.addValue(cluster, "map_sheet", "value 1");
		EntityBuilder.addValue(cluster, "map_sheet", "value 2");
		EntityBuilder.addValue(cluster, "vehicle_location", new Coordinate((double) 12345, (double) 67890, "srs"));
		TextAttribute gpsModel = EntityBuilder.addValue(cluster, "gps_model", "TomTom 1.232");
		gpsModel.getField(0).getState().set(0,true);
		cluster.setChildState("accessibility", 1);
		
		{
			Entity ts = EntityBuilder.addEntity(cluster, "time_study");
			EntityBuilder.addValue(ts, "date", new Date(2011, 2, 14));
			EntityBuilder.addValue(ts, "start_time", new Time(8, 15));
			EntityBuilder.addValue(ts, "end_time", new Time(15, 29));
		}
		{
			Entity ts = EntityBuilder.addEntity(cluster, "time_study");
			EntityBuilder.addValue(ts, "date", new Date(2011, 2, 15));
			EntityBuilder.addValue(ts, "start_time", new Time(8, 32));
			EntityBuilder.addValue(ts, "end_time", new Time(11, 20));
		}
		{
			Entity plot = EntityBuilder.addEntity(cluster, "plot");
			EntityBuilder.addValue(plot, "no", new Code("1"));
			Entity tree1 = EntityBuilder.addEntity(plot, "tree");
			EntityBuilder.addValue(tree1, "tree_no", 1);
			EntityBuilder.addValue(tree1, "dbh", 54.2);
			EntityBuilder.addValue(tree1, "total_height", 2.0);
			// EntityBuilder.addValue(tree1, "bole_height", (Double) null).setMetadata(new
			// CollectAttributeMetadata('*',null,"No value specified"));
			RealAttribute boleHeight = EntityBuilder.addValue(tree1, "bole_height",
					(Double) null);
			boleHeight.getField(0).setSymbol('B');
			boleHeight.getField(0).setRemarks("No value specified");
			Entity tree2 = EntityBuilder.addEntity(plot, "tree");
			EntityBuilder.addValue(tree2, "tree_no", 2);
			EntityBuilder.addValue(tree2, "dbh", 82.8);
			EntityBuilder.addValue(tree2, "total_height", 3.0);
		}
		{
			Entity plot = EntityBuilder.addEntity(cluster, "plot");
			EntityBuilder.addValue(plot, "no", new Code("2"));
			Entity tree1 = EntityBuilder.addEntity(plot, "tree");
			EntityBuilder.addValue(tree1, "tree_no", 1);
			EntityBuilder.addValue(tree1, "dbh", 34.2);
			EntityBuilder.addValue(tree1, "total_height", 2.0);
			Entity tree2 = EntityBuilder.addEntity(plot, "tree");
			EntityBuilder.addValue(tree2, "tree_no", 2);
			EntityBuilder.addValue(tree2, "dbh", 85.8);
			EntityBuilder.addValue(tree2, "total_height", 4.0);
		}
	}
}
