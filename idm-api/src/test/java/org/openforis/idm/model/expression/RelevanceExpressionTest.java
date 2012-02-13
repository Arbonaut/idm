/**
 * 
 */
package org.openforis.idm.model.expression;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RealAttribute;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class RelevanceExpressionTest extends AbstractExpressionTest {
	
	@Test
	public void testTrue() throws InvalidPathException {
		RealAttribute plotDirection = (RealAttribute) cluster.get("plot_direction", 0);
		plotDirection.setValue(345.45);

		String expr = "plot_direction >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, region);
		Assert.assertTrue(b);
	}

	@Test
	public void testFalse() throws InvalidPathException {
		RealAttribute plotDirection = (RealAttribute) cluster.get("plot_direction", 0);
		plotDirection.setValue(385.45);

		String expr = "plot_direction >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, region);
		Assert.assertFalse(b);
	}

	@Test
	public void testDefaultWithMissingNode() throws InvalidPathException {
		RealAttribute plotDirection = (RealAttribute) cluster.get("plot_direction", 0);
		plotDirection.setValue(345.45);

		String expr = "../missing_attr >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, region);
		Assert.assertTrue(b);
	}

	private boolean evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidPathException {
		RelevanceExpression expression = getRecordContext().getExpressionFactory().createRelevanceExpression(expr);
		boolean b = expression.evaluate(context);
		return b;
	}

}
