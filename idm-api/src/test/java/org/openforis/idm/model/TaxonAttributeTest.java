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
public class TaxonAttributeTest {

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
	public void testSetValidLanguageCode() {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		Entity tree = EntityBuilder.addEntity(plot, "tree");
		TaxonOccurrence taxonOccurrence = new TaxonOccurrence("JUG/REG", "Juglans regia", "Noce bianco", "ita", "");
		EntityBuilder.addValue(tree, "species", taxonOccurrence);
	}

	@Test(expected=TaxonAttribute.LanguageCodeNotSupportedException.class)
	public void testSetInvalidLanguageCode() {
		Entity cluster = getRootEntity();
		Entity plot = EntityBuilder.addEntity(cluster, "plot");
		Entity tree = EntityBuilder.addEntity(plot, "tree");
		TaxonOccurrence taxonOccurrence = new TaxonOccurrence("JUG/REG", "Juglans regia", "Noce bianco", "itc", "");
		EntityBuilder.addValue(tree, "species", taxonOccurrence);
	}

	private Entity getRootEntity() {
		Record record = new Record(survey, "2.0");
		Entity entity = record.createRootEntity("cluster");
		return entity;
	}
}
