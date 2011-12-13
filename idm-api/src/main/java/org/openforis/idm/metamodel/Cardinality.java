/**
 * 
 */
package org.openforis.idm.metamodel;

/** 
 * @author M. Togna
 */
public interface Cardinality extends Rule {

	/**
	 * @return  Returns the requiredExpression.
	 * @uml.property  name="requiredExpression" readOnly="true"
	 */
	String getRequiredExpression();

	/**
	 * @return  Returns the minCount.
	 * @uml.property  name="minCount" readOnly="true"
	 */
	Integer getMinCount();

	/**
	 * @return  Returns the maxCount.
	 * @uml.property  name="maxCount" readOnly="true"
	 */
	public Integer getMaxCount();

}
