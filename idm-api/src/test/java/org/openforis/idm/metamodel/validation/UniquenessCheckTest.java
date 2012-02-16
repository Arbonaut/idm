/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.metamodel.validation.ValidationResult;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.TextAttribute;

/**
 * @author M. Togna
 * 
 */
public class UniquenessCheckTest extends ValidatorTest {

	private static final String MAP_SHEET = "map_sheet";

	@Test
	public void testUniqueSingleMapSheet() {
		TextAttribute mapSheet1 = cluster.addValue(MAP_SHEET, "TomTom1");
		ValidationResults results = mapSheet1.validate();
		Assert.assertFalse(containsUniquenessError(results.getErrors(), MAP_SHEET));
	}
	
	@Test
	public void testUniqueMapSheet() {
		TextAttribute mapSheet1 = cluster.addValue(MAP_SHEET, "TomTom1");
		cluster.addValue(MAP_SHEET, "TomTom2");
		ValidationResults results = mapSheet1.validate();
		Assert.assertFalse(containsUniquenessError(results.getErrors(), MAP_SHEET));
	}

	@Test
	public void testUniqueMapSheet2() {
		TextAttribute mapSheet1 = cluster.addValue(MAP_SHEET, "TomTom1");
		cluster.addValue(MAP_SHEET, "TomTom2");
		cluster.addValue(MAP_SHEET, "TomTom2");
		ValidationResults results = mapSheet1.validate();
		Assert.assertFalse(containsUniquenessError(results.getErrors(), MAP_SHEET));
	}
	
	@Test
	public void testNotUniqueMapSheet() {
		cluster.addValue(MAP_SHEET, "TomTom1");
		cluster.addValue(MAP_SHEET, "TomTom2");
		TextAttribute mapSheet3 = cluster.addValue(MAP_SHEET, "TomTom1");
		ValidationResults results = mapSheet3.validate();
		Assert.assertTrue(containsUniquenessError(results.getErrors(), MAP_SHEET));
	}
	
	@Test
	public void testNotUniqueMapSheet2() {
		TextAttribute mapSheet1 = cluster.addValue(MAP_SHEET, "TomTom1");
		cluster.addValue(MAP_SHEET, "TomTom1");
		ValidationResults results = mapSheet1.validate();
		Assert.assertTrue(containsUniquenessError(results.getErrors(), MAP_SHEET));
	}
	
	private boolean containsUniquenessError(List<ValidationResult> results, String name) {
		for (ValidationResult result : results) {
			Validator<?> validator = result.getValidator();
			if (validator instanceof UniquenessCheck) {
				return true;
			}
		}
		return false;
	}
}
