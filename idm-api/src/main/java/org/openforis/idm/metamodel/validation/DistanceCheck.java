/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.geospatial.CoordinateOperations;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.state.NodeState;

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
		return destinationPointExpression;
	}

	public String getMinDistanceExpression() {
		return minDistanceExpression;
	}

	public String getMaxDistanceExpression() {
		return maxDistanceExpression;
	}

	public String getSourcePointExpression() {
		return sourcePointExpression;
	}

	@Override
	public boolean evaluate(NodeState nodeState) {
		try {
			boolean valid = true;
			CoordinateAttribute coordinateAttr = (CoordinateAttribute) nodeState.getNode();
			beforeExecute(coordinateAttr);

			Entity parentEntity = coordinateAttr.getParent();
			SurveyContext recordContext = coordinateAttr.getRecord().getSurveyContext();
			Coordinate from = getCoordinate(recordContext, getSourcePointExpression(), parentEntity, coordinateAttr, coordinateAttr.getValue());
			Coordinate to = getCoordinate(recordContext, getDestinationPointExpression(), parentEntity, coordinateAttr, null);

			double distance = CoordinateOperations.orthodromicDistance(from, to);

			if (maxDistanceExpression != null) {
				double maxDistance = evaluateDistanceExpression(recordContext, parentEntity, coordinateAttr, maxDistanceExpression);
				if (distance > maxDistance) {
					valid = false;
				}
			}
			if (minDistanceExpression != null) {
				double minDistance = evaluateDistanceExpression(recordContext, parentEntity, coordinateAttr, minDistanceExpression);
				if (distance < minDistance) {
					valid = false;
				}
			}

			return valid;
		} catch (Exception e) {
			throw new IdmInterpretationError("Unable to execute distance check", e);
		}
	}

	private double evaluateDistanceExpression(SurveyContext recordContext, Entity context, Attribute<?,?> thisNode, String expression) throws InvalidExpressionException {
		DefaultValueExpression defaultValueExpression = recordContext.getExpressionFactory().createDefaultValueExpression(expression);
		Double value = (Double) defaultValueExpression.evaluate(context, thisNode);
		return value;
	}

	private Coordinate getCoordinate(SurveyContext recordContext, String expression, Node<?> context, Attribute<?,?> thisNode, Coordinate defaultCoordinate) throws InvalidExpressionException {
		if (expression == null) {
			return defaultCoordinate;
		} else {
			DefaultValueExpression valueExpression = recordContext.getExpressionFactory().createDefaultValueExpression(expression);
			Coordinate coordinate = (Coordinate) valueExpression.evaluate(context, thisNode);
			return coordinate;
		}
	}

	private void beforeExecute(CoordinateAttribute coordinate) {
		Survey survey = coordinate.getDefinition().getSurvey();
		List<SpatialReferenceSystem> list = survey.getSpatialReferenceSystems();
		CoordinateOperations.parseSRS(list);
	}

}
