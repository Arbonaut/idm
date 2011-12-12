package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface AttributeDefault {

	/**
	 * @return Returns the expression.
	 * @uml.property name="expression" readOnly="true"
	 */
	public String getExpression();

	/**
	 * @return Returns the condition.
	 * @uml.property name="condition" readOnly="true"
	 */
	public String getCondition();

}
