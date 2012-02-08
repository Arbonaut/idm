package org.openforis.idm.model.expression;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionReferencePathTest extends AbstractExpressionTest {

//	@SuppressWarnings("unused")
//	private Object evaluateExpression(String expr) throws InvalidPathException {
//		Record record = getRecord();
//		Entity rootEntity = record.getRootEntity();
//		String name = rootEntity.getName();
//		Assert.assertEquals("cluster", name);
//
//		DefaultValueExpression expression = getValidationContext().getExpressionFactory().createDefaultValueExpression(expr);
//		Object object = expression.evaluate(rootEntity);
//		return object;
//	}

	@Test
	public void testReferencePath() throws InvalidPathException {
		ExpressionFactory ef = getRecordContext().getExpressionFactory();
		
		String expression = "$this  <= ../../total_height * 8";
		CheckExpression expr = ef.createCheckExpression(expression);
//		boolean eval = expr.evaluate(dbh);
		List<String> paths = expr.getRerferencePaths();
		
		Assert.assertEquals(1, paths.size());
		String resultPath = paths.get(0);
		Assert.assertEquals("../../total_height", resultPath );
		
	}
}
