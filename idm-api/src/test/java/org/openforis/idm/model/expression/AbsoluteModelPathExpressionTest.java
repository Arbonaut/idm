/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
public class AbsoluteModelPathExpressionTest extends AbstractTest {

	@Test
	public void testInterateRoot() throws InvalidExpressionException {
		cluster.addEntity("plot");
		List<Node<?>> list = iterateExpression("/cluster", record);

		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testIteratePath() throws InvalidExpressionException {
		cluster.addEntity("plot");
		cluster.addEntity("plot");
		cluster.addEntity("plot");
		List<Node<?>> list = iterateExpression("/cluster/plot", record);

		Assert.assertEquals(3, list.size());
	}
	
	@Test
	public void testIteratePath2() throws InvalidExpressionException {
		Entity plot1 = cluster.addEntity("plot");
		plot1.addValue("no", new Code("1"));
		Entity plot2 = cluster.addEntity("plot");
		plot2.addValue("no", new Code("1"));
		Entity plot3 = cluster.addEntity("plot");
		plot3.addValue("no", new Code("1"));

		List<Node<?>> list = iterateExpression("/cluster/plot/no", record);

		Assert.assertEquals(3, list.size());
	}


	@Test(expected=InvalidExpressionException.class)
	public void testInvalidExpression() throws InvalidExpressionException {
		Entity plot1 = cluster.addEntity("plot");
		plot1.addValue("no", new Code("1"));
		Entity plot2 = cluster.addEntity("plot");
		plot2.addValue("no", new Code("1"));
		Entity plot3 = cluster.addEntity("plot");
		plot3.addValue("no", new Code("1"));

		iterateExpression("/plot/no", record);
	}
	private List<Node<?>> iterateExpression(String expr, Record record) throws InvalidExpressionException {
		ExpressionFactory expressionFactory = record.getSurveyContext().getExpressionFactory();
		AbsoluteModelPathExpression expression = expressionFactory.createAbsoluteModelPathExpression(expr);
		List<Node<?>> l = expression.iterate(record);
		return l;
	}
}
