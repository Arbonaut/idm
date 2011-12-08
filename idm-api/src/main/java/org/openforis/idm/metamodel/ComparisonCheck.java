package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ComparisonCheck extends Check {

	/**
	 * @return Returns the lessThanExpression.
	 * @uml.property name="lessThanExpression"
	 */
	public String getLessThanExpression();

	/**
	 * @return Returns the lessThanOrEqualsExpression.
	 * @uml.property name="lessThanOrEqualsExpression"
	 */
	public String getLessThanOrEqualsExpression();

	/**
	 * @return Returns the greaterThanExpression.
	 * @uml.property name="greaterThanExpression"
	 */
	public String getGreaterThanExpression();

	/**
	 * @return Returns the greaterThanOrEqualsExpression.
	 * @uml.property name="greaterThanOrEqualsExpression"
	 */
	public String getGreaterThanOrEqualsExpression();

	/**
	 * @return Returns the equalsExpression.
	 * @uml.property name="equalsExpression"
	 */
	public String getEqualsExpression();

}
