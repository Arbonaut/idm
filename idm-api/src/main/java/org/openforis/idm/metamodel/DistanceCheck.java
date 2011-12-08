package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface DistanceCheck extends Check {

	/**
	 * @return Returns the destinationPointExpression.
	 * @uml.property name="destinationPointExpression"
	 */
	public String getDestinationPointExpression();

	/**
	 * @return Returns the minDistanceExpression.
	 * @uml.property name="minDistanceExpression"
	 */
	public String getMinDistanceExpression();

	/**
	 * @return Returns the maxDistanceExpression.
	 * @uml.property name="maxDistanceExpression"
	 */
	public String getMaxDistanceExpression();

	/**
	 * @return Returns the sourcePointExpression.
	 * @uml.property name="sourcePointExpression"
	 */
	public String getSourcePointExpression();

}
