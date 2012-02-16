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
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.InvalidExpressionException;

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

	@Override
	public boolean validate(Attribute<?, ?> node) {
		try {
			boolean valid = true;
			CoordinateAttribute coordinateAttr = (CoordinateAttribute) node;
			beforeExecute(coordinateAttr);

			Entity parentEntity = coordinateAttr.getParent();
			RecordContext recordContext = node.getRecord().getContext();
			Coordinate from = getCoordinate(recordContext, getSourcePointExpression(), parentEntity, node, coordinateAttr.getValue());
			Coordinate to = getCoordinate(recordContext, getDestinationPointExpression(), parentEntity, node, null);

			double distance = CoordinateOperations.orthodromicDistance(from, to);

			if (getMaxDistanceExpression() != null) {
				Double maxDistance = evaluateDistanceExpression(recordContext, parentEntity, node, getMaxDistanceExpression());
				if (distance > maxDistance) {
					valid = false;
				}
			}
			if (getMinDistanceExpression() != null) {
				Double minDistance = evaluateDistanceExpression(recordContext, parentEntity, node, getMinDistanceExpression());
				if (distance < minDistance) {
					valid = false;
				}
			}

			return valid;
		} catch (Exception e) {
			throw new IdmInterpretationError("Unable to execute distance check", e);
		}
	}

	private double evaluateDistanceExpression(RecordContext recordContext, Entity context, Attribute<?,?> thisNode, String expression) throws InvalidExpressionException {
		DefaultValueExpression defaultValueExpression = recordContext.getExpressionFactory().createDefaultValueExpression(expression);
		Double value = (Double) defaultValueExpression.evaluate(context, thisNode);
		return value;
	}

	private Coordinate getCoordinate(RecordContext recordContext, String expression, Node<?> context, Attribute<?,?> thisNode, Coordinate defaultCoordinate) throws InvalidExpressionException {
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
