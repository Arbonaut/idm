package org.openforis.idm.transform;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.metamodel.TimeAttributeDefinition;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.transform2.Cell;
import org.openforis.idm.transform2.Column;
import org.openforis.idm.transform2.FieldColumnProvider;

/**
 * @author G. Miceli
 */
public class FieldColumnProviderTest extends AbstractColumnProviderTest {

	@Test
	public void testTimeAtRoot() throws Exception {
		// Model
		TimeAttributeDefinition timeDefn = schema.createTimeAttributeDefinition();
		timeDefn.setName("time");
		timeDefn.setMultiple(false);
		
		// Data
		TimeAttribute time = (TimeAttribute) timeDefn.createNode();
		time.setValue(new Time(17,16));
		
		// Provider
		FieldColumnProvider hourFcp= new FieldColumnProvider(timeDefn.getFieldDefinition("hour"), null, null);
		FieldColumnProvider minFcp= new FieldColumnProvider(timeDefn.getFieldDefinition("minute"), null, null);
				
		// Test columns
		{
			List<Column> columns = hourFcp.getColumns();
			assertEquals(1, columns.size());
			assertEquals("hour[1]", columns.get(0).getPath().toString());
		}
		
		{
			List<Column> columns2 = minFcp.getColumns();
			assertEquals(1, columns2.size());
			assertEquals("minute[1]", columns2.get(0).getPath().toString());
		}
		
		// Test cells
		{
			List<Cell> cells = hourFcp.getCells(time);
			assertEquals(1, cells.size());
			Cell cell = cells.get(0);
			List<Node<?>> nodes = cell.getNodes();
			assertEquals(1, nodes.size());
			assertEquals(17, ((Field<?>)nodes.get(0)).getValue());
		}
		{
			List<Cell> cells = minFcp.getCells(time);
			assertEquals(1, cells.size());
			Cell cell = cells.get(0);
			List<Node<?>> nodes = cell.getNodes();
			assertEquals(1, nodes.size());
			assertEquals(16, ((Field<?>)nodes.get(0)).getValue());
		}
	}
//	
//	@Test
//	public void testTimeAtInEntity() throws Exception {
//		// Model
//		EntityDefinition plotDef = new EntityDefinition();
//		plotDef.setName("plot");
//		TimeAttributeDefinition timeDefn = new TimeAttributeDefinition();
//		timeDefn.setName("time");
//		timeDefn.setMultiple(false);
//		plotDef.addChildDefinition(timeDefn);
//		
//		// Data
//		Entity plot = (Entity) plotDef.createNode();
//		TimeAttribute time = EntityBuilder.addValue(plot, "time", new Time(17,16));
//		
//		// Provider
//		AttributeColumnProvider timeFcp = new AttributeColumnProvider(timeDefn, null);
//		FieldColumnProvider hourFcp = new FieldColumnProvider(timeDefn.getFieldDefinition("hour"), timeFcp);
//		FieldColumnProvider minFcp= new FieldColumnProvider(timeDefn.getFieldDefinition("minute"), timeFcp);
//				
//		// Test columns
//		{
//			List<Column> columns = hourFcp.getColumns();
//			assertEquals(1, columns.size());
//			assertEquals("time[1]/hour", columns.get(0).getPath().toString());
//		}
//		
//		{
//			List<Column> columns2 = minFcp.getColumns();
//			assertEquals(1, columns2.size());
//			assertEquals("time[1]/minute", columns2.get(0).getPath().toString());
//		}
//		
//		// Test cells
//		{
//			List<Cell> cells = hourFcp.getCells(time);
//			assertEquals(1, cells.size());
//			Cell cell = cells.get(0);
//			List<Node<?>> nodes = cell.getNodes();
//			assertEquals(1, nodes.size());
//			assertEquals(17, ((Field<?>)nodes.get(0)).getValue());
//		}
//		{
//			List<Cell> cells = minFcp.getCells(time);
//			assertEquals(1, cells.size());
//			Cell cell = cells.get(0);
//			List<Node<?>> nodes = cell.getNodes();
//			assertEquals(1, nodes.size());
//			assertEquals(16, ((Field<?>)nodes.get(0)).getValue());
//		}
//	}
}
