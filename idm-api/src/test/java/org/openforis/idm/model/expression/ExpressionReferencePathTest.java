package org.openforis.idm.model.expression;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.AbstractTest;

public class ExpressionReferencePathTest extends AbstractTest {

	@Test
	public void testReferencePath() throws InvalidExpressionException {
		ExpressionFactory ef = cluster.getRecord().getContext().getExpressionFactory();

		String expression = "$this  <= ../../total_height * 8";
		CheckExpression expr = ef.createCheckExpression(expression);
		// boolean eval = expr.evaluate(dbh);
		List<String> paths = expr.getReferencedPaths();

		Assert.assertEquals(1, paths.size());
		String resultPath = paths.get(0);
		Assert.assertEquals("../../total_height", resultPath);

	}
}
