package org.openforis.idm.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openforis.idm.metamodel.Survey;

/**
 * @author G. Miceli
 */
public class EntityTest {

	private static Survey survey;
	
	@BeforeClass
	public static void setUp() throws IOException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		survey = Survey.unmarshal(is);
	}

	@Test
	public void testAddNullAlphanumericCode() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addValue("id", (AlphanumericCode) null);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testAddTooManySingleAttributes() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addValue("id", new AlphanumericCode("123_456"));
		cluster.addValue("id", new AlphanumericCode("789_012"));
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testAddTooManyMultipleEntities() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddAttributeOnEntity() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addValue("plot", new AlphanumericCode("123_456"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddEntityOnAttribute() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addEntity("id");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddUndefinedEntity() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addEntity("xxx");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddUndefinedAttribute() {
		Entity cluster = new Entity(survey.getSchema().getRootEntityDefinition("cluster"));
		cluster.addValue("xxx", 2.0);
	}
}
