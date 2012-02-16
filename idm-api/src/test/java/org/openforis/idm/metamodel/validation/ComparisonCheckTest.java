package org.openforis.idm.metamodel.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class ComparisonCheckTest extends ValidatorTest {

	@Test
	public void testGteFailOnLt() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 0);
		ValidationResults results = crewNo.validate();
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testGtePassOnEq() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 1);
		ValidationResults results = crewNo.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testGtePassOnGt() {
		IntegerAttribute crewNo = cluster.addValue("crew_no", 2);
		ValidationResults results = crewNo.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtFailOnLt() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(8, 00));
		ValidationResults results = endTime.validate();
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtFailOnEq() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(10, 00));
		ValidationResults results = endTime.validate();
		assertTrue(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testTimeGtPassOnGt() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(10, 01));
		ValidationResults results = endTime.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testCodeGtConstant() throws Exception {
		ComparisonCheck check = new ComparisonCheck();
		check.setGreaterThanExpression("-1");
		CodeAttribute region = cluster.addValue("region", new Code("001"));
		boolean validate = check.validate(region);
		assertTrue(validate);
	}

	@Test
	public void testCodeLtConstant() {
		ComparisonCheck check = new ComparisonCheck();
		check.setLessThanExpression("-1");
		CodeAttribute region = cluster.addValue("region", new Code("001"));
		boolean validate = check.validate(region);
		assertFalse(validate);
	}

	@Test
	public void testTextGtConstant() throws Exception {
		ComparisonCheck check = new ComparisonCheck();
		check.setGreaterThanExpression("-1");
		TextAttribute mapSheet = cluster.addValue("map_sheet", "1");
		boolean validate = check.validate(mapSheet);
		assertTrue(validate);
	}

	@Test
	public void testTextLtConstant() {
		ComparisonCheck check = new ComparisonCheck();
		check.setLessThanExpression("-1");
		TextAttribute mapSheet = cluster.addValue("map_sheet", "1");
		boolean validate = check.validate(mapSheet);
		assertFalse(validate);
	}
	
	@Test
	public void testRealPassRange(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 256d);
		ValidationResults results = plotDir.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	@Test
	public void testRealPassRange2(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 0.0);
		ValidationResults results = plotDir.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}
	
	@Test
	public void testRealFailLt(){
		RealAttribute plotDir = cluster.addValue("plot_direction", -52.345d);
		ValidationResults results = plotDir.validate();
		assertTrue(containsComparisonCheck(results.getErrors()));
	}
	
	@Test
	public void testRealFailGt(){
		RealAttribute plotDir = cluster.addValue("plot_direction", 552.345d);
		ValidationResults results = plotDir.validate();
		assertTrue(containsComparisonCheck(results.getErrors()));
	}
	
	private boolean containsComparisonCheck(List<ValidationResult> results) {
		for (ValidationResult result : results) {
			Validator<?> validator = result.getValidator();
			if (validator instanceof ComparisonCheck) {
				return true;
			}
		}
		return false;
	}
}
