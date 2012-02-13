package org.openforis.idm.validation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openforis.idm.metamodel.ComparisonCheck;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.IntegerAttribute;
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
	
	// TODO!
//	@Test
	public void testTimeGtPassOnGt() {
		Entity timeStudy = cluster.addEntity("time_study");
		timeStudy.addValue("start_time", new Time(10, 00));
		TimeAttribute endTime = timeStudy.addValue("end_time", new Time(10, 01));
		ValidationResults results = endTime.validate();
		assertFalse(containsComparisonCheck(results.getErrors()));
	}

	private boolean containsComparisonCheck(List<ValidationResult> results) {
		for (ValidationResult result : results) {
			Validator<?> validator = result.getValidator();
			if ( validator instanceof ComparisonCheck ) {
				return true;
			}
		}
		return false;
	}
}
