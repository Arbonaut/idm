/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;

/**
 * @author M. Togna
 * 
 */
public class DefaultValueExpressionTest extends AbstractExpressionTest {

	private Object evaluateExpression(String expr) throws InvalidExpressionException {
		Record record = getRecord();
		Entity cluster = record.getRootEntity();
		Entity plot = (Entity) cluster.get("plot", 0);
		RecordContext recordContext = getRecordContext();
		ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
		DefaultValueExpression expression = expressionFactory.createDefaultValueExpression(expr);
		Object object = expression.evaluate(plot);
		return object;
	}

	@Test
	public void testAddExpression() throws InvalidExpressionException {
		String expr = "plot[1]/tree[1]/dbh + 1";
		Object object = evaluateExpression(expr);

		Assert.assertEquals(55.2, object);
	}

	@Test
	public void testAddWithParentFuncExpression() throws InvalidExpressionException {
		String expr = "plot[1]/tree[1]/dbh/parent()/dbh + 1";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(55.2, object);
	}

	@Test
	public void testMissingValueExpressionWithOperation() throws InvalidExpressionException {
		String expr = "plot[25]/tree[3]/dbh/parent()/dbh + 4";
		Object object = evaluateExpression(expr);
		Assert.assertNull(object);
	}

	@Test
	public void testMissingValueExpression2() throws InvalidExpressionException {
		String expr = "plot[1]/tree[3]/dbh/parent()/dbh";
		Object object = evaluateExpression(expr);
		Assert.assertNull(object);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidPath() throws InvalidExpressionException {
		String expr = "plot[1]/asdf/tree[3]/dbh/parent()/dbh";
		evaluateExpression(expr);
	}

	@Test
	public void testConstant() throws InvalidExpressionException {
		String expr = "543534";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(Double.valueOf(expr), object);
	}

}
