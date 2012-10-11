/**
 * 
 */
package org.openforis.idm.geospatial;

import java.util.List;

import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.model.Coordinate;

/**
 * @author S. Ricci
 *
 */
public interface CoordinateOperations {

	void parseSRS(List<SpatialReferenceSystem> list);

	void parseSRS(SpatialReferenceSystem srs);

	double orthodromicDistance(double startX, double startY, String startSRSId,
			double destX, double destY, String destSRSId);

	double orthodromicDistance(Coordinate from, Coordinate to);

}