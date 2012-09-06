package org.openforis.idm.transform;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.NumericAttributeDefinition.Type;
import org.openforis.idm.metamodel.Precision;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.EntityBuilder;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.IntegerAttribute;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public class NumberColumnProviderTest {

	@Test
	public void testCollapsedSingleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMultiple(false);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		IntegerAttribute number1 = EntityBuilder.addValue(plot, "number", 1);
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null, null);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(numberDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(1, nodes.size());
		assertEquals(number1, nodes.get(0));
	}

	@Test
	public void testCollapsedMultipleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMaxCount(2);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		IntegerAttribute number1 = EntityBuilder.addValue(plot, "number", 1);
		IntegerAttribute number2 = EntityBuilder.addValue(plot, "number", 2);
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null, null);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(numberDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(2, nodes.size());
		assertEquals(number1, nodes.get(0));
		assertEquals(number2, nodes.get(1));
	}


	@Test
	public void testCollapsedMissingSingleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMultiple(false);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null, null);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(numberDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(0, nodes.size());
	}

	@Test
	public void testCollapsedMissingMultipleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMaxCount(2);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null, null);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(numberDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(0, nodes.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExpandedSingleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMultiple(false);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		EntityBuilder.addValue(plot, "number", 1);
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null);
		provider.setExpandChildren(true);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals("number[1]/value[1]", columns.get(0).getPath().toString());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(1, nodes.size());
		Field<Integer> valueField = (Field<Integer>) nodes.get(0);
		assertEquals((Integer) 1, valueField.getValue());
	}

	@Test
	public void testExpandedMissingSingleInteger() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		numberDefn.setMultiple(false);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null);
		provider.setExpandChildren(true);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals("number[1]/value[1]", columns.get(0).getPath().toString());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(0, nodes.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExpandedSingleIntegerWithUnits() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
		numberDefn.setName("number");
		numberDefn.setType(Type.INTEGER);
		Unit u1 = new Unit();
		u1.setName("m");
		Precision p1 = mock(Precision.class);
		when(p1.getUnit()).thenReturn(u1);
		numberDefn.addPrecisionDefinition(p1);
		Unit u2 = new Unit();
		u2.setName("cm");
		Precision p2 = mock(Precision.class);
		when(p2.getUnit()).thenReturn(u2);
		numberDefn.addPrecisionDefinition(p2);
		numberDefn.setMultiple(false);
		plotDef.addChildDefinition(numberDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		EntityBuilder.addValue(plot, "number", 1, u2);
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null);
		provider.setExpandChildren(true);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(2, columns.size());
		assertEquals("number[1]/value[1]", columns.get(0).getPath().toString());
		assertEquals("number[1]/unit[1]", columns.get(1).getPath().toString());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(2, cells.size());
		assertEquals(1, cells.get(0).getNodes().size());
		Field<Integer> valueField = (Field<Integer>) cells.get(0).getNodes().get(0);
		assertEquals((Integer) 1, valueField.getValue());
		assertEquals(1, cells.get(1).getNodes().size());
		Field<Integer> unitField = (Field<Integer>) cells.get(1).getNodes().get(0);
		assertEquals("cm", unitField.getValue());
	}

	private EntityDefinition createTestPlotRootEntity() {
		Schema schema = new Schema();
		Survey survey = new Survey();
		survey.setSchema(schema);
		EntityDefinition plotDef = new EntityDefinition();
		plotDef.setName("plot");
		schema.addRootEntityDefinition(plotDef);
		return plotDef;
	}
	//
//	@Test
//	public void testExpandedMultipleNumber() throws Exception {
//		// Model
//		EntityDefinition plotDef = new EntityDefinition();
//		plotDef.setName("plot");
//		NumberAttributeDefinition numberDefn = new NumberAttributeDefinition();
//		numberDefn.setName("number");
//		numberDefn.setType(Type.INTEGER);
//		numberDefn.setMaxCount(2);
//		plotDef.addChildDefinition(numberDefn);
//		
//		// Data
//		Entity plot = (Entity) plotDef.createNode();
//		EntityBuilder.addValue(plot, "number", 1);
//		EntityBuilder.addValue(plot, "number", 2);
//		
//		// Provider
//		AttributeColumnProvider provider = new AttributeColumnProvider(numberDefn, null);
//		
//		// Tests
//		List<String> names = provider.getColumnNames();
//		assertEquals(2, names.size());
//		assertEquals("number1", names.get(0));
//		assertEquals("number2", names.get(1));
//		
////		List<Cell> cells = provider.getCells(plot);
////		assertEquals(1, cells.size());
////		Cell cell = cells.get(0);
////		assertEquals(new IntegerValue(1,null), cell.getContents());
//	}

	/*
	@Test
	public void test() throws IOException, InvalidIdmlException {
		Survey survey = loadModel();
		Record record = new Record(survey, "1.0");
		Entity cluster = record.createRootEntity("cluster");
		addTestValues(cluster);
//		System.out.println(cluster);
		Entity plot = (Entity) cluster.get("plot", 0);
		EntityColumnProvider ecpc = new EntityColumnProvider(plot.getDefinition());
		
//		ecpc.expandAll();
		List<Column> cols = ecpc.getColumns();
		System.out.println(cols);
		System.out.println(ecpc.getCells(cluster));

		ecpc.expandAll();
		cols = ecpc.getColumns();
		System.out.println(cols);
		System.out.println(ecpc.getCells(cluster));
	}

	private void addTestValues(Entity cluster) {
		EntityBuilder.addValue(cluster, "id", new Code("123_456"));
		EntityBuilder.addValue(cluster, "gps_realtime", Boolean.TRUE);
		EntityBuilder.addValue(cluster, "region", new Code("001"));
		EntityBuilder.addValue(cluster, "district", new Code("XXX"));	
		EntityBuilder.addValue(cluster, "crew_no", 10);
		EntityBuilder.addValue(cluster, "map_sheet", "value 1");
		EntityBuilder.addValue(cluster, "map_sheet", "value 2");
		EntityBuilder.addValue(cluster, "vehicle_location", new Coordinate((double)432423423l, (double)4324324l, "srs"));
		EntityBuilder.addValue(cluster, "gps_model", "TomTom 1.232");
		cluster.setChildState("accessibility", 1);
		{
			Entity ts = EntityBuilder.addEntity(cluster, "time_study");
			ts.addValue("date", new Date(2011,2,14));
			ts.addValue("start_time", new Time(8,15));
			ts.addValue("end_time", new Time(15,29));
		}
		{
			Entity ts = EntityBuilder.addEntity(cluster, "time_study");
			ts.addValue("date", new Date(2011,2,15));
			ts.addValue("start_time", new Time(8,32));
			ts.addValue("end_time", new Time(11,20));
		}
		{
			Entity plot = EntityBuilder.addEntity(cluster, "plot");
			EntityBuilder.addValue(plot, "no", new Code("1"));
			EntityBuilder.addValue(plot, "subplot", "A");
			EntityBuilder.addValue(plot, "share", 75.0);
			RealAttribute radius = EntityBuilder.addValue(plot, "radius", 12.0);
			radius.setUnitName("m");
			{
				Entity ts = EntityBuilder.addEntity(plot, "time_study");
				ts.addValue("date", new Date(2011,2,15));
				ts.addValue("start_time", new Time(8,32));
				ts.addValue("end_time", new Time(11,20));
			}
			Entity tree1 = EntityBuilder.addEntity(plot, "tree");
			tree1.addValue("tree_no", 1);
			tree1.addValue("dbh", 54.2);
			tree1.addValue("total_height", 2.0);
//			tree1.addValue("bole_height", (Double) null).setMetadata(new CollectAttributeMetadata('*',null,"No value specified"));
			RealAttribute boleHeight = tree1.addValue("bole_height", (Double) null);
			boleHeight.getField(0).setSymbol('*');
			boleHeight.getField(0).setRemarks("No value specified");
			Entity tree2 = EntityBuilder.addEntity(plot, "tree");
			tree2.addValue("tree_no", 2);
			tree2.addValue("dbh", 82.8);
			tree2.addValue("total_height", 3.0);
		}
		{
			Entity plot = EntityBuilder.addEntity(cluster, "plot");
			EntityBuilder.addValue(plot, "no", new Code("2"));
			Entity tree1 = EntityBuilder.addEntity(plot, "tree");
			tree1.addValue("tree_no", 1);
			tree1.addValue("dbh", 34.2);
			tree1.addValue("total_height", 2.0);
			Entity tree2 = EntityBuilder.addEntity(plot, "tree");
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
	*/
}
