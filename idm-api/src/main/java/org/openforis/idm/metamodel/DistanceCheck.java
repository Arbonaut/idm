/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.geotools.CoordinateOperations;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.InvalidPathException;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class DistanceCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "to")
	private String destinationPointExpression;

	@XmlAttribute(name = "min")
	private String minDistanceExpression;

	@XmlAttribute(name = "max")
	private String maxDistanceExpression;

	@XmlAttribute(name = "from")
	private String sourcePointExpression;

	public String getDestinationPointExpression() {
		return this.destinationPointExpression;
	}

	public String getMinDistanceExpression() {
		return this.minDistanceExpression;
	}

	public String getMaxDistanceExpression() {
		return this.maxDistanceExpression;
	}

	public String getSourcePointExpression() {
		return this.sourcePointExpression;
	}

	public boolean execute(RecordContext validationContext, CoordinateAttribute coordinateAttribute) {
		try {
			boolean valid = true;
			beforeExecute(coordinateAttribute);

			Entity parentEntity = coordinateAttribute.getParent();
			Coordinate from = getCoordinate(validationContext, getSourcePointExpression(), parentEntity, coordinateAttribute.getValue());
			Coordinate to = getCoordinate(validationContext, getDestinationPointExpression(), parentEntity, null);

			double distance = CoordinateOperations.orthodromicDistance(from, to);

			if (getMaxDistanceExpression() != null) {
				Double maxDistance = evaluateDistanceExpression(validationContext, parentEntity, getMaxDistanceExpression());
				if (distance > maxDistance) {
					valid = false;
				}
			}
			if (getMinDistanceExpression() != null) {
				Double minDistance = evaluateDistanceExpression(validationContext, parentEntity, getMinDistanceExpression());
				if (distance < minDistance) {
					valid = false;
				}
			}

			return valid;
		} catch (Exception e) {
			throw new RuntimeException("Unable to execute distance check", e);
		}
	}

	private double evaluateDistanceExpression(RecordContext validationContext, Entity context, String expression) throws InvalidPathException {
		DefaultValueExpression defaultValueExpression = validationContext.getExpressionFactory().createDefaultValueExpression(expression);
		Double value = (Double) defaultValueExpression.evaluate(context);
		return value;
	}

	private Coordinate getCoordinate(RecordContext validationContext, String expression, Node<?> context, Coordinate defaultCoordinate) throws InvalidPathException {
		if (expression == null) {
			return defaultCoordinate;
		} else {
			DefaultValueExpression valueExpression = validationContext.getExpressionFactory().createDefaultValueExpression(expression);
			Coordinate coordinate = (Coordinate) valueExpression.evaluate(context);
			return coordinate;
		}
	}

	private void beforeExecute(CoordinateAttribute coordinate) {
		Survey survey = coordinate.getDefinition().getSurvey();
		List<SpatialReferenceSystem> list = survey.getSpatialReferenceSystems();
		CoordinateOperations.parseSRS(list);
	}

}
