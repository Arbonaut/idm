/**
 * 
 */
package org.openforis.idm.model.expression;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RealAttribute;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class RelevanceExpressionTest extends AbstractTest {
	
	protected Entity energySource;
	
	@Before
	public void beforeTest(){
		energySource = household.addEntity("energy_source");
	}
	
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
	public void testBlankValueWithMissingValue2() throws InvalidExpressionException {
		String expr = "plot_direction = ''";
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
	
	@Test
	public void testNotBlankValue() throws InvalidExpressionException {
		String expr = "plot_direction != ''";
		cluster.addValue("plot_direction", (Double) 2.25);
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testNotFunction() throws InvalidExpressionException {
		String expr = "not(plot_direction != 12.8)";
		cluster.addValue("plot_direction", 12.8);
		boolean b = evaluateExpression(expr, cluster);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testEqStringValue() throws InvalidExpressionException {
		energySource.addValue("type", new Code("other"));
		boolean b = evaluateExpression("type='other'", energySource);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testNotEqStringValue() throws InvalidExpressionException {
		energySource.addValue("type", new Code("other"));
		boolean b = evaluateExpression("type!='other'", energySource);
		Assert.assertFalse(b);
	}
	
	@Test
	public void testBooelanValue(){
		Entity plot = cluster.addEntity("plot");
		plot.addEntity("soil");
		boolean relevant = plot.isRelevant("soil");
		Assert.assertTrue(relevant);
	}
	
	@Test
	public void testBooelanValue2(){
		Entity plot = cluster.addEntity("plot");
		plot.addValue("accessibility", new Code("0"));
		plot.addValue("permanent", true);
		plot.addEntity("soil");
		boolean relevant = plot.isRelevant("soil");
		Assert.assertTrue(relevant);
	}
	
	@Test
	public void testBooelanValue3(){
		Entity plot = cluster.addEntity("plot");
		plot.addValue("accessibility", new Code("1"));
		plot.addValue("permanent", true);
		plot.addEntity("soil");
		boolean relevant = plot.isRelevant("soil");
		Assert.assertFalse(relevant);
	}
	
	@Test
	public void testBooelanValue4(){
		Entity plot = cluster.addEntity("plot");
		plot.addValue("accessibility", new Code("0"));
		plot.addValue("permanent", false);
		plot.addEntity("soil");
		boolean relevant = plot.isRelevant("soil");
		Assert.assertFalse(relevant);
	}
	
	private boolean evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidExpressionException {
		RelevanceExpression expression = context.getRecord().getSurveyContext().getExpressionFactory().createRelevanceExpression(expr);
		boolean b = expression.evaluate(context, null);
		return b;
	}

}
