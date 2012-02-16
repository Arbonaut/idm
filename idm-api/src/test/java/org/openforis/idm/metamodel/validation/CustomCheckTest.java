/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.RealAttribute;

/**
 * @author M. Togna
 * 
 */
public class CustomCheckTest extends AbstractTest {

	@Test
	public void testPass() {
		Entity plot = cluster.addEntity("plot");
		Entity tree = plot.addEntity("tree");
		RealAttribute totalHeight = tree.addValue("total_height", 16.0);
		tree.addValue("dbh", 2.0);

		ValidationResults results = totalHeight.validate();
		assertFalse(containsCustomCheck(results.getWarnings()));
	}

	@Test
	public void testPassLtEq() {
		Entity plot = cluster.addEntity("plot");
		Entity tree = plot.addEntity("tree");

		RealAttribute totalHeight = tree.addValue("total_height", 16.0);
		tree.addValue("dbh", 16.0);

		ValidationResults results = totalHeight.validate();
		assertFalse(containsCustomCheck(results.getWarnings()));
	}

	@Test
	public void testPassLtEqWithCondition1() {
		Entity plot = cluster.addEntity("plot");
		Entity tree = plot.addEntity("tree");
		tree.addValue("health", new Code("1"));
		RealAttribute totalHeight = tree.addValue("total_height", 16.0);
		tree.addValue("dbh", 16.0);

		ValidationResults results = totalHeight.validate();
		assertFalse(containsCustomCheck(results.getWarnings()));
	}

	@Test
	public void testFailLtEqWithCondition() {
		Entity plot = cluster.addEntity("plot");
		Entity tree = plot.addEntity("tree");
		RealAttribute totalHeight = tree.addValue("total_height", 2.0);
		tree.addValue("dbh", 16.5);
		tree.addValue("health", new Code("1"));
		ValidationResults results = totalHeight.validate();
		assertTrue(containsCustomCheck(results.getWarnings()));
	}

	@Test
	public void testPassLtEqWithCondition() {
		Entity plot = cluster.addEntity("plot");
		Entity tree = plot.addEntity("tree");
		RealAttribute totalHeight = tree.addValue("total_height", 2.0);
		tree.addValue("dbh", 16.5);
		tree.addValue("health", new Code("2"));
		ValidationResults results = totalHeight.validate();
		assertFalse(containsCustomCheck(results.getWarnings()));
	}

	private boolean containsCustomCheck(List<ValidationResult> results) {
		for (ValidationResult result : results) {
			if (result.getValidator() instanceof CustomCheck) {
				return true;
			}
		}
		return false;
	}

}
