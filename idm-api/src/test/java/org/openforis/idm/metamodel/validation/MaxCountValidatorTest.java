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
		boolean result = cluster.validateMaxCount("crew_no");
		assertFalse(result);
//		List<ValidationResult> errors = results.getErrors();
//		assertTrue(containsMaxCountError(errors, "crew_no"));
	}

	@Test
	public void testMultipleAttributeLessThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		boolean result = cluster.validateMaxCount("map_sheet");
		assertTrue(result);
	}

	@Test
	public void testMultipleAttributeMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		boolean result = cluster.validateMaxCount("map_sheet");
		assertTrue(result);
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleAttributeMoreThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		cluster.addValue("map_sheet", "D");
		boolean result = cluster.validateMaxCount("map_sheet");
		assertFalse(result);
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleSingleEntity() {
		Entity plot = cluster.addEntity("plot");
		plot.addEntity("centre");
		plot.addEntity("centre");
		boolean result = plot.validateMaxCount("centre");
		assertFalse(result);
	}

	@Test
	public void testMultipleEntityLessThanMax() {
		cluster.addEntity("time_study");
		boolean result = cluster.validateMaxCount("time_study");
		assertTrue(result);
	}

	@Test
	public void testMultipleEntityMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		boolean result = cluster.validateMaxCount("time_study");
		assertTrue(result);
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleEntityMoreThanMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		boolean result = cluster.validateMaxCount("time_study");
		assertFalse(result);
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
