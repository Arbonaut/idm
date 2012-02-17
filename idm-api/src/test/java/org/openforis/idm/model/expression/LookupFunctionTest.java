/**
 * 
 */
package org.openforis.idm.model.expression;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.AbstractTest;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;

/**
 * @author M. Togna
 * 
 */
public class LookupFunctionTest extends AbstractTest {

	public static Coordinate TEST_COORDINATE = Coordinate.parseCoordinate("SRID=EPSG:21035;POINT(805750 9333820)");

	@Test
	public void testLookupFunction1Arg() throws InvalidExpressionException {
		Record record = cluster.getRecord();
		RecordContext recordContext = record.getContext();

		String expr = "idm:lookup('sampling_design', 'plot_centre', 'id', 1)";

		DefaultValueExpression expression = recordContext.getExpressionFactory().createDefaultValueExpression(expr);
		Object object = expression.evaluate(cluster, null);
		Assert.assertEquals(TEST_COORDINATE, object);

	}

	@Test
	public void testLookupFunction2Arg() throws InvalidExpressionException {
		Record record = cluster.getRecord();
		RecordContext recordContext = record.getContext();

		String expr = "idm:lookup('sampling_design', 'plot_centre', 'id')";
		DefaultValueExpression expression = recordContext.getExpressionFactory().createDefaultValueExpression(expr);
		Object object = expression.evaluate(cluster, null);
		Assert.assertEquals(TEST_COORDINATE, object);
	}
}
