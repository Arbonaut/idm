/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
public class ModelPathExpressionTest extends AbstractExpressionTest {

	@Test
	public void testIteratePath() throws InvalidPathException {
		Record record = getRecord();
		Entity entity = record.getRootEntity();

		String expr = "plot";
		List<Node<?>> list = iterateExpression(expr, entity);

		Assert.assertEquals(3, list.size());
	}

	@Test
	public void testIteratePath2() throws InvalidPathException {
		Record record = getRecord();
		Entity entity = record.getRootEntity();

		String expr = "plot/no";
		List<Node<?>> list = iterateExpression(expr, entity);

		Assert.assertEquals(3, list.size());
	}

	@Test
	public void testIteratePath3() throws InvalidPathException {
		Record record = getRecord();
		Entity entity = record.getRootEntity();

		String expr = "time_study";
		List<Node<?>> list = iterateExpression(expr, entity);

		Assert.assertEquals(2, list.size());
	}

	@SuppressWarnings("unused")
	private Object evaluateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidPathException {
		ModelPathExpression expression = getValidationContext().getExpressionFactory().createModelPathExpression(expr);
		Object o = expression.evaluate(context);
		return o;
	}

	private List<Node<?>> iterateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidPathException {
		ModelPathExpression expression = getValidationContext().getExpressionFactory().createModelPathExpression(expr);
		List<Node<?>> l = expression.iterate(context);
		return l;
	}
}
