/**
 * 
 */
package org.openforis.idm.model.expression;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RealAttribute;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class RelevanceExpressionTest extends AbstractTest {

	@Test
	public void testTrue() throws InvalidExpressionException {
		cluster.addValue("plot_direction", 345.45);
		String expr = "plot_direction >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}

	@Test
	public void testFalse() throws InvalidExpressionException {
		cluster.addValue("plot_direction", 385.45);

		String expr = "plot_direction >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertFalse(b);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testDefaultWithMissingNode() throws InvalidExpressionException {
		cluster.addValue("plot_direction", 345.45);

		String expr = "parent()/missing_attr >= 0 and plot_direction <= 359";
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}

	@Test
	public void testRelevanceOnNodeExpression() throws InvalidExpressionException {
		cluster.addValue("plot_direction", 345.45);
		RealAttribute plotDistance = cluster.addValue("plot_distance", 12.2);
		String expr = "parent()/plot_direction";
		boolean b = evaluateExpression(expr, plotDistance);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testRelevanceOnNegativeNodeExpression() throws InvalidExpressionException {
		cluster.addValue("plot_direction", 345.45);
		RealAttribute plotDistance = cluster.addValue("plot_distance", 12.2);
		String expr = "not(parent()/plot_direction)";
		boolean b = evaluateExpression(expr, plotDistance);
		Assert.assertFalse(b);
	}
	
	@Test
	public void testBlankValueWithMissingValue() throws InvalidExpressionException {
		String expr = "plot_direction != ''";
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testBlankValue() throws InvalidExpressionException {
		String expr = "plot_direction != ''";
		cluster.addValue("plot_direction", (Double)null);
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}
	
	private boolean evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidExpressionException {
		RelevanceExpression expression = context.getRecord().getSurveyContext().getExpressionFactory().createRelevanceExpression(expr);
		boolean b = expression.evaluate(context, null);
		return b;
	}

}
