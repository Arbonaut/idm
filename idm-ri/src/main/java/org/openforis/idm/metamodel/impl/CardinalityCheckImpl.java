/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import org.openforis.idm.metamodel.CardinalityCheck;

/**
 * @author M. Togna
 * 
 */
public class CardinalityCheckImpl extends AbstractImplicitCheck implements CardinalityCheck {

	private String requiredExpression;
	private Integer minCount;
	private Integer maxCount;

	@Override
	public String getRequiredExpression() {
		return this.requiredExpression;
	}

	@Override
	public void setRequiredExpression(String requiredExpression) {
		this.requiredExpression = requiredExpression;
	}

	@Override
	public Integer getMinCount() {
		return this.minCount;
	}

	@Override
	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	@Override
	public Integer getMaxCount() {
		return this.maxCount;
	}

	@Override
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

}
