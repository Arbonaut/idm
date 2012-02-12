package org.openforis.idm.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.IdmlBindingContext;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyUnmarshaller;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TestRecordContext;

/**
 * @author G. Miceli
 */
public class MinCountValidatorTest {

	private static Survey survey;

	@BeforeClass
	public static void setUp() throws IOException, InvalidIdmlException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		IdmlBindingContext idmlBindingContext = new IdmlBindingContext();
		SurveyUnmarshaller su = idmlBindingContext.createSurveyUnmarshaller();
		survey = su.unmarshal(is);
	}

	@Test
	public void testMissingOptionalSingleAttribute() {
		Entity cluster = createCluster();
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsNode(errors, "crew_no"));
	}

	@Test
	public void testMissingRequiredSingleAttribute() {
		Entity cluster = createCluster();
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "region"));
	}

	@Test
	public void testSpecifiedRequiredSingleAttribute() {
		Entity cluster = createCluster();
		cluster.addValue("region", new Code("001"));
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsNode(errors, "region"));
	}


	@Test
	public void testMissingMultipleRequiredAttribute() {
		Entity cluster = createCluster();
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "map_sheet"));
	}

	@Test
	public void testEmptyMultipleRequiredAttribute() {
		Entity cluster = createCluster();
		cluster.addValue("map_sheet", "");
		cluster.addValue("map_sheet", "");
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "map_sheet"));
	}

	@Test
	public void testTooFewMultipleRequiredAttribute() {
		Entity cluster = createCluster();
		cluster.addValue("map_sheet", "");
		cluster.addValue("map_sheet", "567");
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "map_sheet"));
	}

	@Test
	public void testMultipleRequiredAttribute() {
		Entity cluster = createCluster();
		cluster.addValue("map_sheet", "123");
		cluster.addValue("map_sheet", "567");
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsNode(errors, "map_sheet"));
	}

	@Test
	public void testMissingRequiredMultipleEntity() {
		Entity cluster = createCluster();
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "time_study"));
	}

	@Test
	public void testEmptyRequiredMultipleEntity() {
		Entity cluster = createCluster();
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("date", (Date) null);
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsNode(errors, "time_study"));
	}

	@Test
	public void testSpecifiedRequiredMultipleEntity() {
		Entity cluster = createCluster();
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("date", new Date(2012, 1, 1));
		ValidationResults results = cluster.validate();
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsNode(errors, "time_study"));
	}

	private boolean containsNode(List<ValidationResult> errors, String name) {
		for (ValidationResult result : errors) {
			MinCountValidator v = (MinCountValidator) result.getValidator();
			NodeDefinition nodeDefinition = v.getNodeDefinition();
			if ( nodeDefinition.getName().equals(name) ) {
				return true;
			}
		}
		return false;
	}
	
	private Entity createCluster() {
		Record record = new Record(new TestRecordContext(), survey, "2.0");
		Entity entity = record.createRootEntity("cluster");
		return entity;
	}
}
