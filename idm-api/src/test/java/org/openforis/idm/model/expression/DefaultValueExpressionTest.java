/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.AbstractTest;

/**
 * @author M. Togna
 * 
 */
public class DefaultValueExpressionTest extends AbstractTest {

	private Object evaluateExpression(String expr) throws InvalidExpressionException {
		ExpressionFactory expressionFactory = cluster.getRecord().getContext().getExpressionFactory();
		DefaultValueExpression expression = expressionFactory.createDefaultValueExpression(expr);
		Object object = expression.evaluate(cluster, null);
		return object;
	}

	@Test
	public void testAddExpression() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "plot[1]/tree[1]/dbh + 1";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(55.2, object);
	}

	@Test
	public void testAddWithParentFuncExpression() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "plot[1]/tree[1]/dbh/parent()/dbh + 1";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(55.2, object);
	}

	@Test
	public void testMissingValueExpressionWithOperation() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "plot[25]/tree[3]/dbh/parent()/dbh + 4";
		Object object = evaluateExpression(expr);
		Assert.assertNull(object);
	}

	@Test
	public void testMissingValueExpression2() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "plot[1]/tree[3]/dbh/parent()/dbh";
		Object object = evaluateExpression(expr);
		Assert.assertNull(object);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidPath() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "plot[1]/asdf/tree[3]/dbh/parent()/dbh";
		Object object = evaluateExpression(expr);
		System.err.println(object);
	}

	@Test
	public void testConstant() throws InvalidExpressionException {
		cluster.addEntity("plot").addEntity("tree").addValue("dbh", 54.2);
		
		String expr = "543534";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(Double.valueOf(expr), object);
	}

}
