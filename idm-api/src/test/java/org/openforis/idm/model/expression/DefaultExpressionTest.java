/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
public class DefaultExpressionTest extends AbstractExpressionTest {

	private Object evaluateExpression(String expr) throws InvalidPathException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		String name = rootEntity.getName();
		Assert.assertEquals("cluster", name);

		DefaultValueExpression expression = new DefaultValueExpression(expr);
		Object object = expression.evaluate(rootEntity);
		return object;
	}

	@Test
	public void testAddExpression() throws InvalidPathException {
		String expr = "plot[1]/tree[1]/dbh + 1";
		Object object = evaluateExpression(expr);

		Assert.assertEquals(55.2, object);
	}

	@Test
	public void testAddWithParentFuncExpression() throws InvalidPathException {
		String expr = "plot[1]/tree[1]/dbh/parent()/dbh + 1";
		Object object = evaluateExpression(expr);
		Assert.assertEquals(55.2, object);
	}

	@Test(expected = MissingValueException.class)
	public void testMissingValueExpressionWithOperation() throws InvalidPathException {
		String expr = "plot[25]/tree[3]/dbh/parent()/dbh + 4";
		evaluateExpression(expr);
	}

	@Test(expected = MissingValueException.class)
	public void testMissingValueExpression2() throws InvalidPathException {
		String expr = "plot[1]/tree[3]/dbh/parent()/dbh";
		evaluateExpression(expr);
	}

	@Test(expected = InvalidPathException.class)
	public void testInvalidPath() throws InvalidPathException {
		String expr = "plot[1]/asdf/tree[3]/dbh/parent()/dbh";
		evaluateExpression(expr);
	}

}
