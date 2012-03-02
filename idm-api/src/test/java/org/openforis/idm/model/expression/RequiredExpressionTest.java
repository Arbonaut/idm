/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * 
 */
public class RequiredExpressionTest extends AbstractTest {

	@Test
	public void testTrue() throws InvalidExpressionException {
		CodeAttribute region = cluster.addValue("region", new Code("004"));
		String expr = "true()";
		boolean b = evaluateExpression(expr, region);
		Assert.assertTrue(b);
	}

	@Test
	public void testFalse() throws InvalidExpressionException {
		CodeAttribute region = cluster.addValue("region", new Code("004"));
		String expr = "false()";
		boolean b = evaluateExpression(expr, region);
		Assert.assertFalse(b);
	}

	@Test
	public void testSubPlotRequired() throws InvalidExpressionException {
		Entity plot = cluster.addEntity("plot");
		plot.addValue("share", 12.4);

		String expr = "share < 100";
		boolean b = evaluateExpression(expr, plot);
		Assert.assertTrue(b);
	}

	@Test
	public void testSubPlotNotRequired() throws InvalidExpressionException {
		cluster.addEntity("plot");
		cluster.addEntity("plot");
		Entity plot = cluster.addEntity("plot");
		plot.addValue("share", 120.4);

		String expr = "share < 100";
		boolean b = evaluateExpression(expr, plot);
		Assert.assertFalse(b);
	}

	private boolean evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidExpressionException {
		RequiredExpression expression = context.getRecord().getSurveyContext().getExpressionFactory().createRequiredExpression(expr);
		boolean b = expression.evaluate(context, null);
		return b;
	}

}
