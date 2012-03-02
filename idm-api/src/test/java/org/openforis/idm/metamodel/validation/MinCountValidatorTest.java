package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.Entity;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class MinCountValidatorTest extends ValidationTest {

	@Test
	public void testMissingOptionalSingleAttribute() {
//		ValidationResults results = validate(cluster);
		boolean valid = cluster.validateMinCount("crew_no");
		assertTrue(valid);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "crew_no"));
	}

	@Test
	public void testMissingRequiredSingleAttribute() {
		boolean valid = cluster.validateMinCount("region");
		assertFalse(valid);
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertTrue(containsMinCountError(errors, "region"));
	}

	@Test
	public void testSpecifiedRequiredSingleAttribute() {
		cluster.addValue("region", new Code("001"));
		boolean valid = cluster.validateMinCount("region");
		assertTrue(valid);
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "region"));
	}

	@Test
	public void testMissingMultipleRequiredAttribute() {
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "map_sheet"));
//		
		boolean valid = cluster.validateMinCount("map_sheet");
		assertTrue(valid);
	}

	@Test
	public void testEmptyMultipleRequiredAttribute() {
		cluster.addValue("map_sheet", "");
		cluster.addValue("map_sheet", "");
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "map_sheet"));
		
		boolean valid = cluster.validateMinCount("map_sheet");
		assertTrue(valid);
	}

	@Test
	public void testTooFewMultipleRequiredAttribute() {
		cluster.addValue("map_sheet", "");
		cluster.addValue("map_sheet", "567");
		
		boolean valid = cluster.validateMinCount("map_sheet");
		assertTrue(valid);
		
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "map_sheet"));
	}

	@Test
	public void testMultipleRequiredAttribute() {
		cluster.addValue("map_sheet", "123");
		cluster.addValue("map_sheet", "567");
		
		boolean valid = cluster.validateMinCount("map_sheet");
		assertTrue(valid);
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "map_sheet"));
	}

	@Test
	public void testMissingRequiredMultipleEntity() {
		boolean valid = cluster.validateMinCount("time_study");
		assertFalse(valid);
		
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertTrue(containsMinCountError(errors, "time_study"));
	}

	@Test
	public void testEmptyRequiredMultipleEntity() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("date", (Date) null);
		
		boolean valid = cluster.validateMinCount("time_study");
		assertFalse(valid);
		
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertTrue(containsMinCountError(errors, "time_study"));
	}

	@Test
	public void testSpecifiedRequiredMultipleEntity() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("date", new Date(2012, 1, 1));
//		ValidationResults results = validate(cluster);
//		List<ValidationResult> errors = results.getErrors();
//		assertFalse(containsMinCountError(errors, "time_study"));
		
		boolean valid = cluster.validateMinCount("time_study");
		assertTrue(valid);
	}

//	private boolean containsMinCountError(List<ValidationResult> errors, String name) {
//		for (ValidationResult result : errors) {
//			ValidationRule<?> validator = result.getValidator();
//			if (validator instanceof MinCountValidator) {
//				MinCountValidator v = (MinCountValidator) validator;
//				NodeDefinition nodeDefinition = v.getNodeDefinition();
//				if (nodeDefinition.getName().equals(name)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

}
