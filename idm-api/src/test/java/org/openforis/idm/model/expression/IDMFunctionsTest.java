/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 *
 */
public class IDMFunctionsTest extends AbstractTest {

	@Test
	public void testBlankMissingNode() throws InvalidExpressionException{
		String expr = "idm:blank(id)";
		boolean b = evaluateRelevance(cluster, null, expr);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testBlankNullValue() throws InvalidExpressionException{
		cluster.addValue("id", (Code) null);
		
		String expr = "idm:blank(id)";
		boolean b = evaluateRelevance(cluster, null, expr);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testBlankBlankValue() throws InvalidExpressionException{
		cluster.addValue("id",  new Code(""));
		
		String expr = "idm:blank(id)";
		boolean b = evaluateRelevance(cluster, null, expr);
		Assert.assertTrue(b);
	}
	
	@Test
	public void testBlankValidCode() throws InvalidExpressionException{
		cluster.addValue("id",  new Code("001"));
		
		String expr = "idm:blank(id)";
		boolean b = evaluateRelevance(cluster, null, expr);
		Assert.assertFalse(b);
	}
	
	@Test
	public void testBlankValidNumber() throws InvalidExpressionException{
		cluster.addValue("plot_direction",  3442.45);
		
		String expr = "not( idm:blank( plot_direction ) )";
		boolean b = evaluateRelevance(cluster, null, expr);
		Assert.assertTrue(b);
	}
	
	public boolean evaluateRelevance(Node<?> context, Node<?> thisNode, String expr) throws InvalidExpressionException{
		ExpressionFactory expressionFactory = context.getRecord().getSurveyContext().getExpressionFactory();
		RelevanceExpression expression = expressionFactory.createRelevanceExpression(expr);
		boolean b = expression.evaluate(context, thisNode);
		return b;
	}
	
	
	
}
