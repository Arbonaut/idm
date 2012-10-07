package org.openforis.idm.path;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.IdmlValidator;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.EntityBuilder;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RealAttribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TestSurveyContext;

/**
 * @author G. Miceli
 */
public class PathTest {

	private static Survey survey;

	@BeforeClass
	public static void setUp() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext(new TestSurveyContext());
		IdmlValidator su = idmlBindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}

	@Test
	public void testSingleAttributeWithIndex() throws InvalidPathException {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		Entity tree1 = EntityBuilder.addEntity(plot, "tree");
		EntityBuilder.addValue(tree1, "dbh", 12.2);
		Entity tree2 = EntityBuilder.addEntity(plot, "tree");
		RealAttribute dbh2 = EntityBuilder.addValue(tree2, "dbh", 15.7);
		
		Path path = Path.parsePath("tree[2]/dbh[1]");
		
		// Node
		List<Node<?>> res = path.evaluate(plot);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(dbh2, res.get(0));
		
		// Defn
		NodeDefinition def = path.evaluate(plot.getDefinition());
		Assert.assertEquals(dbh2.getDefinition(), def);
	}

	@Test
	public void testSingleAttributeWithoutIndex() throws InvalidPathException {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		Entity tree1 = EntityBuilder.addEntity(plot, "tree");
		EntityBuilder.addValue(tree1, "dbh", 12.2);
		Entity tree2 = EntityBuilder.addEntity(plot, "tree");
		RealAttribute dbh2 = EntityBuilder.addValue(tree2, "dbh", 15.7);
		
		Path path = Path.parsePath("tree[2]/dbh");
		
		// Node
		List<Node<?>> res = path.evaluate(plot);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(dbh2, res.get(0));
		
		// Defn
		NodeDefinition def = path.evaluate(plot.getDefinition());
		Assert.assertEquals(dbh2.getDefinition(), def);
	}

	@Test
	public void testMultipleAttributeWithoutIndex() throws InvalidPathException {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		EntityBuilder.addEntity(plot, "tree");
		Entity tree2 = EntityBuilder.addEntity(plot, "tree");
		RealAttribute dbh1 = EntityBuilder.addValue(tree2, "dbh", 12.2);
		RealAttribute dbh2 = EntityBuilder.addValue(tree2, "dbh", 15.7);
		
		Path path = Path.parsePath("tree[2]/dbh");
		
		// Node
		List<Node<?>> res = path.evaluate(plot);
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(dbh1, res.get(0));
		Assert.assertEquals(dbh2, res.get(1));
		
		// Defn
		NodeDefinition def = path.evaluate(plot.getDefinition());
		Assert.assertEquals(dbh2.getDefinition(), def);
	}

	@Test
	public void testMultipleFieldPathWithIndex() throws InvalidPathException {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		EntityBuilder.addEntity(plot, "tree");
		Entity tree2 = EntityBuilder.addEntity(plot, "tree");
		EntityBuilder.addValue(tree2, "dbh", 12.2);
		RealAttribute dbh2 = EntityBuilder.addValue(tree2, "dbh", 15.7);
		
		Path path = Path.parsePath("tree[2]/dbh[2]/value");
		
		// Node
		List<Node<?>> res = path.evaluate(plot);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(15.7, ((Field<?>)res.get(0)).getValue());
		
		// Defn
		NodeDefinition def = path.evaluate(plot.getDefinition());
		Assert.assertEquals(dbh2.getDefinition().getFieldDefinition("value"), def);
	}

	@Test
	public void testMultipleFieldPathWithoutIndex() throws InvalidPathException {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		EntityBuilder.addEntity(plot, "tree");
		Entity tree2 = EntityBuilder.addEntity(plot, "tree");
		RealAttribute dbh1 = EntityBuilder.addValue(tree2, "dbh", 12.2);
		RealAttribute dbh2 = EntityBuilder.addValue(tree2, "dbh", 15.7);
		
		Path path = Path.parsePath("tree[2]/dbh/value");
		
		// Node
		List<Node<?>> res = path.evaluate(plot);
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(12.2, ((Field<?>)res.get(0)).getValue());
		Assert.assertEquals(15.7, ((Field<?>)res.get(1)).getValue());
		
		// Defn
		NodeDefinition def = path.evaluate(plot.getDefinition());
		Assert.assertEquals(dbh1.getDefinition().getFieldDefinition("value"), def);
		Assert.assertEquals(dbh2.getDefinition().getFieldDefinition("value"), def);
	}

	private Entity getRootEntity() {
		Record record = new Record(survey, "2.0");
		Entity entity = record.createRootEntity("cluster");
		return entity;
	}
}
