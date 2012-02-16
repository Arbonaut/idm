/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;

/**
 * @author M. Togna
 * 
 */
public class CoordinateValidator implements Validator<CoordinateAttribute> {

	@Override
	public boolean validate(CoordinateAttribute node) {
		Coordinate coordinate = node.getValue();
		CoordinateAttributeDefinition definition = node.getDefinition();
		List<SpatialReferenceSystem> srs = definition.getSurvey().getSpatialReferenceSystems();
		
		return coordinate.getX() != null && coordinate.getY() != null && isSrsIdValid(srs, coordinate.getSrsId());
	}

	private boolean isSrsIdValid(List<SpatialReferenceSystem> srs, String srsId) {
		for (SpatialReferenceSystem spatialReferenceSystem : srs) {
			if (spatialReferenceSystem.getId().equals(srsId)) {
				return true;
			}
		}
		return false;
	}

}
