package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CardinalityCheck extends Check {
	/**
	 * @return  Returns the requiredExpression.
	 * @uml.property  name="requiredExpression"
	 */
	public String getRequiredExpression();

	/**
	 * Setter of the property <tt>requiredExpression</tt>
	 * @param requiredExpression  The requiredExpression to set.
	 * @uml.property  name="requiredExpression"
	 */
	public void setRequiredExpression(String requiredExpression);

	/**
	 * @return  Returns the minCount.
	 * @uml.property  name="minCount"
	 */
	public Integer getMinCount();

	/**
	 * Setter of the property <tt>minCount</tt>
	 * @param minCount  The minCount to set.
	 * @uml.property  name="minCount"
	 */
	public void setMinCount(Integer minCount);

	/**
	 * @return  Returns the maxCount.
	 * @uml.property  name="maxCount"
	 */
	public Integer getMaxCount();

	/**
	 * Setter of the property <tt>maxCount</tt>
	 * @param maxCount  The maxCount to set.
	 * @uml.property  name="maxCount"
	 */
	public void setMaxCount(Integer maxCount);

}
