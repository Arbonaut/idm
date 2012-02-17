/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * 
 */
public class ModelPathExpressionTest extends AbstractTest {

	@Test
	public void testIteratePath() throws InvalidExpressionException {
		String entityName = "plot";
		cluster.addEntity(entityName);
		cluster.addEntity(entityName);
		cluster.addEntity(entityName);
		List<Node<?>> list = iterateExpression(entityName, cluster);

		Assert.assertEquals(3, list.size());
	}

	@Test(expected = InvalidExpressionException.class)
	public void testIterateInvalidPath() throws InvalidExpressionException {
		String entityName = "plot";
		cluster.addEntity(entityName);
		cluster.addEntity(entityName);
		cluster.addEntity(entityName);
		String expr = "plot^2";
		List<Node<?>> list = iterateExpression(expr, cluster);

		Assert.assertEquals(3, list.size());
	}

	@Test
	public void testIteratePath2() throws InvalidExpressionException {
		String entityName = "plot";
		Entity plot1 = cluster.addEntity(entityName);
		plot1.addValue("no", new Code("1"));
		Entity plot2 = cluster.addEntity(entityName);
		plot2.addValue("no", new Code("1"));
		Entity plot3 = cluster.addEntity(entityName);
		plot3.addValue("no", new Code("1"));

		String expr = "plot/no";
		List<Node<?>> list = iterateExpression(expr, cluster);

		Assert.assertEquals(3, list.size());
	}

	@Test
	public void testIteratePath3() throws InvalidExpressionException {
		String entityName = "time_study";
		cluster.addEntity(entityName);
		cluster.addEntity(entityName);
		List<Node<?>> list = iterateExpression(entityName, cluster);

		Assert.assertEquals(2, list.size());
	}

	private List<Node<?>> iterateExpression(String expr, Node<? extends NodeDefinition> context) throws InvalidExpressionException {
		ModelPathExpression expression = context.getRecord().getContext().getExpressionFactory().createModelPathExpression(expr);
		List<Node<?>> l = expression.iterate(context, null);
		return l;
	}
}
