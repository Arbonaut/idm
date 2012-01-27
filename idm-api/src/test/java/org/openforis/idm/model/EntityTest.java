package org.openforis.idm.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.BindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;

/**
 * @author G. Miceli
 */
public class EntityTest {

	private static Survey survey;
	
	@BeforeClass
	public static void setUp() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		BindingContext bindingContext = new BindingContext();
		SurveyUnmarshaller su = bindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}

	@Test
	public void testAddNullCode() {
		Entity cluster = getRootEntity();
		cluster.addValue("id", (Code) null);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testAddTooManySingleAttributes() {
		Entity cluster = getRootEntity();
		cluster.addValue("id", new Code("123_456"));
		cluster.addValue("id", new Code("789_012"));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testAddTooManyMultipleEntities() {
		Entity cluster = getRootEntity();
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddAttributeOnEntity() {
		Entity cluster = getRootEntity();
		cluster.addValue("plot", new Code("123_456"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEntityOnAttribute() {
		Entity cluster = getRootEntity();
		cluster.addEntity("id");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddUndefinedEntity() {
		Entity cluster = getRootEntity();
		cluster.addEntity("xxx");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddUndefinedAttribute() {
		Entity cluster = getRootEntity();
		cluster.addValue("xxx", 2.0);
	}

	private Entity getRootEntity() {
		Record record = new Record(survey, "cluster", "2.0");
		Entity entity = record.getRootEntity();
		return entity;
	}
}
