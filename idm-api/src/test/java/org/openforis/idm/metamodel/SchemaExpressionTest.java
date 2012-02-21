/**
 * 
 */
package org.openforis.idm.metamodel;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.URL;

import org.openforis.idm.metamodel.expression.SchemaPathExpression;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;

/**
 * @author M. Togna
 * 
 */
public class SchemaExpressionTest {

	private static Survey survey;

	// @BeforeClass
	public static void setUp() throws Exception {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext();
		SurveyUnmarshaller su = idmlBindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}

	// @Test
	public void testRootEntityDefinition() {
		EntityDefinition cluster = survey.getSchema().getRootEntityDefinitions().get(0);
		assertEquals("cluster", cluster.getName());
	}

	// @Test
	public void testExpression() {
		EntityDefinition cluster = survey.getSchema().getRootEntityDefinitions().get(0);

		SchemaPathExpression expression = new SchemaPathExpression("plot/tree");
		Object obj = expression.evaluate(cluster);
		assertEquals(EntityDefinition.class, obj.getClass());

		EntityDefinition tree = (EntityDefinition) obj;
		assertEquals("tree", tree.getName());
	}
	/*
	 * @Test public void testSODGetMethod() { EntityDefinition cluster = survey.getSchema().getRootEntityDefinitions().get(0); EntityDefinition tree =
	 * (EntityDefinition) cluster.getAll("plot/tree"); assertEquals("tree", tree.getName()); }
	 * 
	 * @Test public void testSchemaGetMethod() { Schema schema = survey.getSchema(); EntityDefinition tree = (EntityDefinition)
	 * schema.getAll("cluster/plot/tree"); assertEquals("tree", tree.getName()); }
	 * 
	 * @Test public void testParent() { EntityDefinition cluster = survey.getSchema().getRootEntityDefinitions().get(0); EntityDefinition tree =
	 * (EntityDefinition) cluster.getAll("plot/tree"); assertEquals("tree", tree.getName());
	 * 
	 * EntityDefinition plot = (EntityDefinition) tree.getAll("parent()"); assertEquals("plot", plot.getName());
	 * 
	 * EntityDefinition cluster1 = (EntityDefinition) tree.getAll("parent()/parent()"); assertEquals("cluster", cluster1.getName()); }
	 */
}
