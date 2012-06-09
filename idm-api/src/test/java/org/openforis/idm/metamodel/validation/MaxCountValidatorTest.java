package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openforis.idm.model.Entity;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class MaxCountValidatorTest extends ValidationTest {

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleSingleAttribute() {
		cluster.addValue("crew_no", 1);
		cluster.addValue("crew_no", 2);
		//ValidationResults results = validate(cluster);
		ValidationResultFlag result = cluster.validateMaxCount("crew_no");
		assertFalse(result.isOk());
//		List<ValidationResult> errors = results.getErrors();
//		assertTrue(containsMaxCountError(errors, "crew_no"));
	}

	@Test
	public void testMultipleAttributeLessThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		ValidationResultFlag result = cluster.validateMaxCount("map_sheet");
		assertTrue(result.isOk());
	}

	@Test
	public void testMultipleAttributeMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		ValidationResultFlag result = cluster.validateMaxCount("map_sheet");
		assertTrue(result.isOk());
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleAttributeMoreThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		cluster.addValue("map_sheet", "D");
		ValidationResultFlag result = cluster.validateMaxCount("map_sheet");
		assertFalse(result.isOk());
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleSingleEntity() {
		Entity plot = cluster.addEntity("plot");
		plot.addEntity("centre");
		plot.addEntity("centre");
		ValidationResultFlag result = plot.validateMaxCount("centre");
		assertFalse(result.isOk());
	}

	@Test
	public void testMultipleEntityLessThanMax() {
		cluster.addEntity("time_study");
		ValidationResultFlag result = cluster.validateMaxCount("time_study");
		assertTrue(result.isOk());
	}

	@Test
	public void testMultipleEntityMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		ValidationResultFlag result = cluster.validateMaxCount("time_study");
		assertTrue(result.isOk());
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleEntityMoreThanMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		ValidationResultFlag result = cluster.validateMaxCount("time_study");
		assertFalse(result.isOk());
	}

//	protected boolean containsMaxCountError(List<ValidationResult> errors, String name) {
//		for (ValidationResult result : errors) {
//			ValidationRule validator = result.getValidator();
//			if (validator instanceof MaxCountValidator) {
//				MaxCountValidator v = (MaxCountValidator) validator;
//				NodeDefinition nodeDefinition = v.getNodeDefinition();
//				if (nodeDefinition.getName().equals(name)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
