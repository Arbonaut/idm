/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import org.openforis.idm.metamodel.Cardinality;

/**
 * @author M. Togna
 * 
 */
public class CardinalityImpl implements Cardinality {
	
	private Integer minCount;
	private Integer maxCount;
	private String requiredExpression;

	public Integer getMinCount() {
		return minCount;
	}

	void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public String getRequiredExpression() {
		return requiredExpression;
	}

	void setRequiredExpression(String requiredExpression) {
		this.requiredExpression = requiredExpression;
	}

}
