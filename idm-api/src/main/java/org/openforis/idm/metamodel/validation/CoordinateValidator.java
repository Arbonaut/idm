/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class CoordinateValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		CoordinateAttribute node = (CoordinateAttribute) nodeState.getNode();
		Coordinate coordinate = node .getValue();
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
