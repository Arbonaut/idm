/**
 * 
 */
package org.openforis.idm.model.expression;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.RealAttribute;

/**
 * @author M. Togna
 * 
 */
public class CheckConditionExpressionTest extends AbstractTest {

	@Test
	public void testTrue() throws InvalidExpressionException {
		RealAttribute plotDirection =  cluster.addValue("plot_direction", 345.45);

		String expr = "$this >= 0 and $this <= 359";
		boolean b = evaluateExpression(expr, plotDirection);
		Assert.assertTrue(b);
	}

	@Test
	public void testFalse() throws InvalidExpressionException {
		RealAttribute plotDirection =  cluster.addValue("plot_direction", 385.45);
		
		String expr = "$this >= 0 and $this <= 359";
		boolean b = evaluateExpression(expr, plotDirection);
		Assert.assertFalse(b);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testDefaultWithMissingNode() throws InvalidExpressionException {
		RealAttribute plotDirection =  cluster.addValue("plot_direction", 345.45);
		
		String expr = "parent()/missing_attr >= 0 and $this <= 359";
		boolean b = evaluateExpression(expr, plotDirection);
		Assert.assertFalse(b);
	}

	private boolean evaluateExpression(String expr, Attribute<?,?> thisNode) throws InvalidExpressionException {
		CheckConditionExpression expression = thisNode.getRecord().getContext().getExpressionFactory().createCheckConditionExpression(expr);
		boolean b = expression.evaluate(thisNode.getParent(), thisNode);
		return b;
	}
}
