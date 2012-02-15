/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
public class RequiredExpressionTest extends AbstractExpressionTest {

	@Test
	public void testTrue() throws InvalidExpressionException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		CodeAttribute region = (CodeAttribute) rootEntity.get("region", 0);
		String expr = "true()";
		boolean b = evaluateExpression(expr, region);
		Assert.assertTrue(b);
	}

	@Test
	public void testFalse() throws InvalidExpressionException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		CodeAttribute region = (CodeAttribute) rootEntity.get("region", 0);
		String expr = "false()";
		boolean b = evaluateExpression(expr, region);
		Assert.assertFalse(b);
	}

	@Test
	public void testSubPlotRequired() throws InvalidExpressionException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		Entity plot = (Entity) rootEntity.get("plot", 0);

		String expr = "share < 100";

		boolean b = evaluateExpression(expr, plot);
		Assert.assertTrue(b);
	}

	@Test
	public void testSubPlotNotRequired() throws InvalidExpressionException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		Entity plot = (Entity) rootEntity.get("plot", 2);

		String expr = "share < 100";
		boolean b = evaluateExpression(expr, plot);
		Assert.assertFalse(b);
	}
	
	@Test
	public void test() throws InvalidExpressionException{
		String expr = "number('e') > 10";
		DefaultValueExpression expression = getExpressionFactory().createDefaultValueExpression(expr);
		Object object = expression.evaluate(null, null);
		System.out.println(object);
	}

	@SuppressWarnings("unused")
	private boolean evaluateExpression(String expr) throws InvalidExpressionException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		return evaluateExpression(expr, rootEntity);
	}

	private boolean evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidExpressionException {
		RequiredExpression expression = getRecordContext().getExpressionFactory().createRequiredExpression(expr);
		boolean b = expression.evaluate(context, null);
		return b;
	}

}
