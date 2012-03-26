package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openforis.idm.metamodel.validation.ValidationResultFlag.ERROR;
import static org.openforis.idm.metamodel.validation.ValidationResultFlag.OK;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.IntegerAttribute;
import org.openforis.idm.model.RealAttribute;
import org.openforis.idm.model.TextAttribute;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author G. Miceli
 */
public class ComparisonCheckTest extends ValidationTest {

	@Test
	public void testGteFailOnLt() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 0);
		ValidationResults results = validate(crewNo);
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testGtePassOnEq() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 1);
		ValidationResults results = validate(crewNo);
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testGtePassOnGt() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 2);
		ValidationResults results = validate(crewNo);
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtFailOnLt() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(8, 00));
		ValidationResults results =  validate(endTime);
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtFailOnEq() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(10, 00));
		ValidationResults results = validate(endTime);
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtPassOnGt() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(10, 01));
		ValidationResults results = validate(endTime);
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testCodeGtConstant() throws Exception {
		ComparisonCheck check = new ComparisonCheck();
		check.setGreaterThanExpression("-1");
		CodeAttribute region = cluster.addValue("region", new Code("001"));
		ValidationResultFlag result = check.evaluate(region);
		assertEquals(OK, result);
	}

	@Test
	public void testCodeLtConstant() {
		ComparisonCheck check = new ComparisonCheck();
		check.setLessThanExpression("-1");
		CodeAttribute region = cluster.addValue("region", new Code("001"));
		ValidationResultFlag result = check.evaluate(region);
		assertEquals(ERROR, result);
	}

	@Test
	public void testTextGtConstant() throws Exception {
		ComparisonCheck check = new ComparisonCheck();
		check.setGreaterThanExpression("-1");
		TextAttribute mapSheet = cluster.addValue("map_sheet", "1");
		ValidationResultFlag result = check.evaluate(mapSheet);
		assertEquals(OK, result);
	}

	@Test
	public void testTextLtConstant() {
		ComparisonCheck check = new ComparisonCheck();
		check.setLessThanExpression("-1");
		TextAttribute mapSheet = cluster.addValue("map_sheet", "1");
		ValidationResultFlag result = check.evaluate(mapSheet);
		assertEquals(ERROR, result);
	}
	
	@Test
	public void testRealPassRange(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 256d);
		ValidationResults results =  validate(plotDir);
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testRealPassRange2(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 0.0);
		ValidationResults results = validate(plotDir);
		assertFalse(containsComparisonCheck(results.getErrors()));
	}
	
	@Test
	public void testRealFailLt(){
		RealAttribute plotDir = cluster.addValue("plot_direction", -52.345d);
		ValidationResults results =  validate(plotDir);
		assertTrue(containsComparisonCheck(results.getErrors()));
	}
	
	@Test
	public void testRealFailGt(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 552.345d);
		ValidationResults results =  validate(plotDir);
		assertTrue(containsComparisonCheck(results.getErrors()));
	}
	
	private boolean containsComparisonCheck(List<ValidationResult> results) {
		for (ValidationResult result : results) {
			ValidationRule<?> validator = result.getValidator();
			if (validator instanceof ComparisonCheck) {
				return true;
			}
		}
		return false;
	}
}
