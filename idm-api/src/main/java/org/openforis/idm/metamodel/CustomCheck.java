package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CustomCheck extends ExplicitCheck {

	/**
	 * @return  Returns the expression.
	 * @uml.property  name="expression"
	 */
	public String getExpression();

	/**
	 * Setter of the property <tt>expression</tt>
	 * @param expression  The expression to set.
	 * @uml.property  name="expression"
	 */
	public void setExpression(String expression);

}
