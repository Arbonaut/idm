/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openforis.idm.geospatial.CoordinateOperations;
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

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 * @author S. Ricci
 */
public class DistanceCheck extends Check<CoordinateAttribute> {

	private static CoordinateOperations coordinateOperations;

	static {
		ServiceLoader<CoordinateOperations> serviceLoader = ServiceLoader.load(CoordinateOperations.class);
		Iterator<CoordinateOperations> it = serviceLoader.iterator();
		if ( it.hasNext() ) {
			coordinateOperations = it.next();
		}
	}

	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DistanceCheck.class);

	private String destinationPointExpression;
	private String minDistanceExpression;
	private String maxDistanceExpression;
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

	public void setDestinationPointExpression(String destinationPointExpression) {
		this.destinationPointExpression = destinationPointExpression;
	}

	public void setMinDistanceExpression(String minDistanceExpression) {
		this.minDistanceExpression = minDistanceExpression;
	}

	public void setMaxDistanceExpression(String maxDistanceExpression) {
		this.maxDistanceExpression = maxDistanceExpression;
	}

	public void setSourcePointExpression(String sourcePointExpression) {
		this.sourcePointExpression = sourcePointExpression;
	}

	@Override
	public ValidationResultFlag evaluate(CoordinateAttribute coordinateAttr) {
		if ( coordinateOperations == null ) {
			return ValidationResultFlag.OK;
		} else {
			try {
				boolean valid = true;
				beforeExecute(coordinateAttr);

				Entity parentEntity = coordinateAttr.getParent();
				SurveyContext recordContext = coordinateAttr.getRecord().getSurveyContext();
				Coordinate from = getCoordinate(recordContext, getSourcePointExpression(), parentEntity, coordinateAttr, coordinateAttr.getValue());
				Coordinate to = getCoordinate(recordContext, getDestinationPointExpression(), parentEntity, coordinateAttr, null);

				if ( !(from == null || to == null) ) {
					double distance = coordinateOperations.orthodromicDistance(from, to);

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
				}

				return ValidationResultFlag.valueOf(valid, this.getFlag());
			} catch (Exception e) {
	//			throw new IdmInterpretationError("Unable to execute distance check", e);
				if( LOG.isInfoEnabled() ){
					LOG.info("Unable to evaluate distance check " , e);
				}
				return ValidationResultFlag.OK;
			}
		}
	}

	private double evaluateDistanceExpression(SurveyContext recordContext, Entity context, Attribute<?, ?> thisNode, String expression) throws InvalidExpressionException {
		DefaultValueExpression defaultValueExpression = recordContext.getExpressionFactory().createDefaultValueExpression(expression);
		Double value = (Double) defaultValueExpression.evaluate(context, thisNode);
		return value;
	}

	private Coordinate getCoordinate(SurveyContext recordContext, String expression, Node<?> context, Attribute<?, ?> thisNode, Coordinate defaultCoordinate) throws InvalidExpressionException {
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
		coordinateOperations.parseSRS(list);
	}

}