package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface UniquenessCheck extends Check {

	/**
	 * @return Returns the expression.
	 * @uml.property name="expression"
	 */
	public String getExpression();

}
