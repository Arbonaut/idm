package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;

/**
 * @author G. Miceli
 */
public class MaxCountValidatorTest extends ValidationTest {

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleSingleAttribute() {
		cluster.addValue("crew_no", 1);
		cluster.addValue("crew_no", 2);
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsMaxCountError(errors, "crew_no"));
	}

	@Test
	public void testMultipleAttributeLessThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsMaxCountError(errors, "map_sheet"));
	}

	@Test
	public void testMultipleAttributeMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsMaxCountError(errors, "map_sheet"));
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleAttributeMoreThanMax() {
		cluster.addValue("map_sheet", "A");
		cluster.addValue("map_sheet", "B");
		cluster.addValue("map_sheet", "C");
		cluster.addValue("map_sheet", "D");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsMaxCountError(errors, "map_sheet"));
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleSingleEntity() {
		Entity plot = cluster.addEntity("plot");
		plot.addEntity("centre");
		plot.addEntity("centre");
		ValidationResults results = validate(plot);
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsMaxCountError(errors, "centre"));
	}

	@Test
	public void testMultipleEntityLessThanMax() {
		cluster.addEntity("time_study");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsMaxCountError(errors, "time_study"));
	}

	@Test
	public void testMultipleEntityMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertFalse(containsMaxCountError(errors, "time_study"));
	}

	@Test
	// (expected=ArrayIndexOutOfBoundsException.class)
	public void testMultipleEntityMoreThanMax() {
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		cluster.addEntity("time_study");
		ValidationResults results = validate(cluster);
		List<ValidationResult> errors = results.getErrors();
		assertTrue(containsMaxCountError(errors, "time_study"));
	}

	protected boolean containsMaxCountError(List<ValidationResult> errors, String name) {
		for (ValidationResult result : errors) {
			ValidationRule validator = result.getValidator();
			if (validator instanceof MaxCountValidator) {
				MaxCountValidator v = (MaxCountValidator) validator;
				NodeDefinition nodeDefinition = v.getNodeDefinition();
				if (nodeDefinition.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
