/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.TextAttribute;

/**
 * @author M. Togna
 * 
 */
public class PatternCheckTest extends ValidationTest {

	@Test
	public void testUpperCaseLetterPatternPass() {
		Entity plot = cluster.addEntity("plot");
		TextAttribute subplot = plot.addValue("subplot", "D");
		ValidationResults results = validate(subplot);
		assertFalse(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testUpperCaseLetterPatternFail() {
		Entity plot = cluster.addEntity("plot");
		TextAttribute subplot = plot.addValue("subplot", "d");
		ValidationResults results = validate(subplot);
		assertTrue(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testUpperCaseLetterPatternFail2() {
		Entity plot = cluster.addEntity("plot");
		TextAttribute subplot = plot.addValue("subplot", "4");
		ValidationResults results = validate(subplot);
		assertTrue(containsPatternCheck(results.getErrors()));
	}

	// household id: (X-)?[1-9][0-9]*
	@Test
	public void testValidPattern() {
		TextAttribute id = household.addValue("id", "X-1");
		ValidationResults results = validate(id);
		assertFalse(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testValidPattern2() {
		TextAttribute id = household.addValue("id", "X-102357");
		ValidationResults results = validate(id);
		assertFalse(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testValidPattern3() {
		TextAttribute id = household.addValue("id", "102357");
		ValidationResults results = validate(id);
		assertFalse(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testInvalidPattern() {
		TextAttribute id = household.addValue("id", "x-102357");
		ValidationResults results = validate(id);
		assertTrue(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testInvalidPattern2() {
		TextAttribute id = household.addValue("id", "X-1d02357");
		ValidationResults results = validate(id);
		assertTrue(containsPatternCheck(results.getErrors()));
	}

	@Test
	public void testInvalidPattern3() {
		TextAttribute id = household.addValue("id", "X-");
		ValidationResults results = validate(id);
		assertTrue(containsPatternCheck(results.getErrors()));
	}

	private boolean containsPatternCheck(List<ValidationResult> results) {
		for (ValidationResult result : results) {
			if (result.getValidator() instanceof PatternCheck) {
				return true;
			}
		}
		return false;
	}
}
