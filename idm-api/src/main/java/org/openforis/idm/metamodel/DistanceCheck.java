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
	 * Setter of the property <tt>destinationPointExpression</tt>
	 * 
	 * @param destinationPointExpression
	 *            The destinationPointExpression to set.
	 * @uml.property name="destinationPointExpression"
	 */
	public void setDestinationPointExpression(String destinationPointExpression);

	/**
	 * @return Returns the minDistanceExpression.
	 * @uml.property name="minDistanceExpression"
	 */
	public String getMinDistanceExpression();

	/**
	 * Setter of the property <tt>minDistanceExpression</tt>
	 * 
	 * @param minDistanceExpression
	 *            The minDistanceExpression to set.
	 * @uml.property name="minDistanceExpression"
	 */
	public void setMinDistanceExpression(String minDistanceExpression);

	/**
	 * @return Returns the maxDistanceExpression.
	 * @uml.property name="maxDistanceExpression"
	 */
	public String getMaxDistanceExpression();

	/**
	 * Setter of the property <tt>maxDistanceExpression</tt>
	 * 
	 * @param maxDistanceExpression
	 *            The maxDistanceExpression to set.
	 * @uml.property name="maxDistanceExpression"
	 */
	public void setMaxDistanceExpression(String maxDistanceExpression);

	/**
	 * @return Returns the sourcePointExpression.
	 * @uml.property name="sourcePointExpression"
	 */
	public String getSourcePointExpression();

	/**
	 * Setter of the property <tt>sourcePointExpression</tt>
	 * 
	 * @param sourcePointExpression
	 *            The sourcePointExpression to set.
	 * @uml.property name="sourcePointExpression"
	 */
	public void setSourcePointExpression(String sourcePointExpression);

}
