package org.openforis.idm.transform;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.EntityBuilder;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author G. Miceli
 */
public class TimeColumnProviderTest {

	@Test
	public void testSingleTime() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMultiple(false);
		plotDef.addChildDefinition(timeDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		TimeAttribute time = EntityBuilder.addValue(plot, "time", new Time(17,16));
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(timeDefn, null);
				
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(timeDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(1, nodes.size());
		assertEquals(time, nodes.get(0));
	}

	@Test
	public void testExpandMultipleTimeInstances() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMaxCount(2);
		plotDef.addChildDefinition(timeDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		TimeAttribute time1 = EntityBuilder.addValue(plot, "time", new Time(9,37));
		TimeAttribute time2 = EntityBuilder.addValue(plot, "time", new Time(17,16));
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(timeDefn, null);
		provider.setExpandMultiple(true);

		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(2, columns.size());
		assertEquals(timeDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());
		assertEquals(timeDefn, ((NodeColumnProvider) columns.get(1).getProvider()).getNodeDefinition());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(2, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(1, nodes.size());
		assertEquals(time1, nodes.get(0));
		cell = cells.get(1);
		nodes = cell.getNodes();
		assertEquals(1, nodes.size());
		assertEquals(time2, nodes.get(0));
	}

	@Test
	public void testExpandSingleTimeFields() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMultiple(false);
		plotDef.addChildDefinition(timeDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		TimeAttribute time = EntityBuilder.addValue(plot, "time", new Time(17,16));

		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(timeDefn, null);
		provider.setExpandChildren(true);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(2, columns.size());
		assertEquals("time[1]/hour[1]", columns.get(0).getPath().toString());
		assertEquals("time[1]/minute[1]", columns.get(1).getPath().toString());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(2, cells.size());
		assertEquals(1, cells.get(0).getNodes().size());
		assertEquals(time.getField("hour"), cells.get(0).getNodes().get(0));
		assertEquals(time.getField("minute"), cells.get(1).getNodes().get(0));
	}

	@Test
	public void testMissingSingleTime() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMultiple(false);
		plotDef.addChildDefinition(timeDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(timeDefn, null);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(1, columns.size());
		assertEquals(timeDefn, ((NodeColumnProvider) columns.get(0).getProvider()).getNodeDefinition());

		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(1, cells.size());
		Cell cell = cells.get(0);
		List<Node<?>> nodes = cell.getNodes();
		assertEquals(0, nodes.size());
	}

	@Test
	public void testExpandMissingSingleTimeFields() throws Exception {
		// Model
		EntityDefinition plotDef = createTestPlotRootEntity();
		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMultiple(false);
		plotDef.addChildDefinition(timeDefn);
		
		// Data
		Entity plot = (Entity) plotDef.createNode();
		
		// Provider
		AttributeColumnProvider provider = new AttributeColumnProvider(timeDefn, null);
		provider.setExpandChildren(true);
		
		// Test columns
		List<Column> columns = provider.getColumns();
		assertEquals(2, columns.size());
		assertEquals("time[1]/hour[1]", columns.get(0).getPath().toString());
		assertEquals("time[1]/minute[1]", columns.get(1).getPath().toString());
		
		// Test cells
		List<Cell> cells = provider.getCells(plot);
		assertEquals(2, cells.size());
		assertEquals(0, cells.get(0).getNodes().size());
		assertEquals(0, cells.get(1).getNodes().size());
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


}
