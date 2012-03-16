/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;

/**
 * @author M. Togna
 * 
 */
public class DistanceCheckTest extends ValidationTest {

	@Test
	public void testValidMaxDistance() {
		String coordStr = "SRID=EPSG:21035;POINT(805750 9333820)";
		Coordinate coord = Coordinate.parseCoordinate(coordStr);
		cluster.addValue("id",new Code("001"));
		CoordinateAttribute vehicleLocation = cluster.addValue("vehicle_location", coord);
		ValidationResults results = validate(vehicleLocation);
		Assert.assertFalse(containsDistanceCheck(results.getErrors()));
	}

	@Test
	public void testErrorMaxDistance() {
		String coordStr = "SRID=EPSG:21035;POINT(915750 9333820)";
		Coordinate coord = Coordinate.parseCoordinate(coordStr);
		cluster.addValue("id",new Code("001"));
		CoordinateAttribute vehicleLocation = cluster.addValue("vehicle_location", coord);
		ValidationResults results = validate(vehicleLocation);
		Assert.assertTrue(containsDistanceCheck(results.getErrors()));
	}

	@Test
	public void testWarnMaxDistance() {
		String coordStr = "SRID=EPSG:21035;POINT(885750 9333820)";
		Coordinate coord = Coordinate.parseCoordinate(coordStr);
		cluster.addValue("id",new Code("001"));
		CoordinateAttribute vehicleLocation = cluster.addValue("vehicle_location", coord);
		ValidationResults results = validate(vehicleLocation);
		Assert.assertFalse(containsDistanceCheck(results.getErrors()));
		Assert.assertTrue(containsDistanceCheck(results.getWarnings()));
	}

	private boolean containsDistanceCheck(List<ValidationResult> errors) {
		for (ValidationResult result : errors) {
			if (result.getValidator() instanceof DistanceCheck) {
				return true;
			}
		}
		return false;
	}

}
