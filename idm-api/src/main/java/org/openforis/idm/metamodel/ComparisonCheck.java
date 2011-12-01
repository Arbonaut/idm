package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ComparisonCheck extends ExplicitCheck {

	/**
	 * @return Returns the lessThanExpression.
	 * @uml.property name="lessThanExpression"
	 */
	public String getLessThanExpression();

	/**
	 * Setter of the property <tt>lessThanExpression</tt>
	 * 
	 * @param lessThanExpression
	 *            The lessThanExpression to set.
	 * @uml.property name="lessThanExpression"
	 */
	public void setLessThanExpression(String lessThanExpression);

	/**
	 * @return Returns the lessThanOrEqualsExpression.
	 * @uml.property name="lessThanOrEqualsExpression"
	 */
	public String getLessThanOrEqualsExpression();

	/**
	 * Setter of the property <tt>lessThanOrEqualsExpression</tt>
	 * 
	 * @param lessThanOrEqualsExpression
	 *            The lessThanOrEqualsExpression to set.
	 * @uml.property name="lessThanOrEqualsExpression"
	 */
	public void setLessThanOrEqualsExpression(String lessThanOrEqualsExpression);

	/**
	 * @return Returns the greaterThanExpression.
	 * @uml.property name="greaterThanExpression"
	 */
	public String getGreaterThanExpression();

	/**
	 * Setter of the property <tt>greaterThanExpression</tt>
	 * 
	 * @param greaterThanExpression
	 *            The greaterThanExpression to set.
	 * @uml.property name="greaterThanExpression"
	 */
	public void setGreaterThanExpression(String greaterThanExpression);

	/**
	 * @return Returns the greaterThanOrEqualsExpression.
	 * @uml.property name="greaterThanOrEqualsExpression"
	 */
	public String getGreaterThanOrEqualsExpression();

	/**
	 * Setter of the property <tt>greaterThanOrEqualsExpression</tt>
	 * 
	 * @param greaterThanOrEqualsExpression
	 *            The greaterThanOrEqualsExpression to set.
	 * @uml.property name="greaterThanOrEqualsExpression"
	 */
	public void setGreaterThanOrEqualsExpression(String greaterThanOrEqualsExpression);

	/**
	 * @return Returns the equalsExpression.
	 * @uml.property name="equalsExpression"
	 */
	public String getEqualsExpression();

	/**
	 * Setter of the property <tt>equalsExpression</tt>
	 * 
	 * @param equalsExpression
	 *            The equalsExpression to set.
	 * @uml.property name="equalsExpression"
	 */
	public void setEqualsExpression(String equalsExpression);

}
