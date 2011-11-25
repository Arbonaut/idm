package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface AttributeDefault {

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

	/**
	 * @return  Returns the condition.
	 * @uml.property  name="condition"
	 */
	public String getCondition();

	/**
	 * Setter of the property <tt>condition</tt>
	 * @param condition  The condition to set.
	 * @uml.property  name="condition"
	 */
	public void setCondition(String condition);

}
