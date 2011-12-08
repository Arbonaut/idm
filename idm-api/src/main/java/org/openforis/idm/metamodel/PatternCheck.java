package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface PatternCheck extends Check {

	/**
	 * @return Returns the regularExpression.
	 * @uml.property name="regularExpression"
	 */
	public String getRegularExpression();

	/**
	 * Setter of the property <tt>regularExpression</tt>
	 * 
	 * @param regularExpression
	 *            The regularExpression to set.
	 * @uml.property name="regularExpression"
	 */
	public void setRegularExpression(String regularExpression);

}
