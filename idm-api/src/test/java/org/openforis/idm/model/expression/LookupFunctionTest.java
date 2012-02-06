/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
public class LookupFunctionTest extends AbstractExpressionTest {

	static Coordinate TEST_COORDINATE = Coordinate.parseCoordinate("SRID=32632;POINT(0 0)");

	@Test
	public void testLookupFunction() throws InvalidPathException {
		Record record = getRecord();
		Entity rootEntity = record.getRootEntity();
		String expr = "idm:lookup('sampling_design', 'plot_centre', 'id', 1)";

		DefaultValueExpression expression = getValidationContext().getExpressionFactory().createDefaultValueExpression(expr);
		Object object = expression.evaluate(rootEntity);
		Assert.assertEquals(TEST_COORDINATE, object);

		expr = "idm:lookup('sampling_design', 'plot_centre', 'id')";
		expression = getValidationContext().getExpressionFactory().createDefaultValueExpression(expr);
		object = expression.evaluate(rootEntity);
		Assert.assertEquals(TEST_COORDINATE, object);
	}

}
