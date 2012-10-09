package org.openforis.idm.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.IdmlValidator;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class EntityTest {

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
	public void testAddNullCode() {
		Entity cluster = getRootEntity();
		EntityBuilder.addValue(cluster, "id", (Code) null);
	}

	// @Test(expected = ArrayIndexOutOfBoundsException.class)
	// public void testAddTooManySingleAttributes() {
	// Entity cluster = getRootEntity();
	// EntityBuilder.addValue(cluster, "id", new Code("123_456"));
	// EntityBuilder.addValue(cluster, "id", new Code("789_012"));
	// }

	// @Test(expected = ArrayIndexOutOfBoundsException.class)
	// public void testAddTooManyMultipleEntities() {
	// Entity cluster = getRootEntity();
	// EntityBuilder.addEntity(cluster, "time_study");
	// EntityBuilder.addEntity(cluster, "time_study");
	// EntityBuilder.addEntity(cluster, "time_study");
	// }

	@Test(expected = IllegalArgumentException.class)
	public void testAddAttributeOnEntity() {
		Entity cluster = getRootEntity();
		EntityBuilder.addValue(cluster, "plot", new Code("123_456"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEntityOnAttribute() {
		Entity cluster = getRootEntity();
		EntityBuilder.addEntity(cluster, "id");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddUndefinedEntity() {
		Entity cluster = getRootEntity();
		EntityBuilder.addEntity(cluster, "xxx");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddUndefinedAttribute() {
		Entity cluster = getRootEntity();
		EntityBuilder.addValue(cluster, "xxx", 2.0);
	}

//	@Test
//	public void testValidateRootEntity() {
//		Entity cluster = getRootEntity();
//		State nodeState = new State(cluster);
//		ValidationResults results = new Validator().validate(nodeState);
//		int errors = results.getErrors().size();
//		Assert.assertEquals(5, errors);
//	}

//	@Test
//	public void testValidatePlot() {
//		Entity cluster = getRootEntity();
//		Entity plot = EntityBuilder.addEntity(cluster, "plot");
//		EntityBuilder.addValue(plot, "share", 20.0);
//		
//		State nodeState = new State(plot);
//		ValidationResults results = new Validator().validate(nodeState);
//		int errors = results.getErrors().size();
//		Assert.assertEquals(16, errors);
//	}

	private Entity getRootEntity() {
		Record record = new Record(survey, "2.0");
		Entity entity = record.createRootEntity("cluster");
		return entity;
	}
}
