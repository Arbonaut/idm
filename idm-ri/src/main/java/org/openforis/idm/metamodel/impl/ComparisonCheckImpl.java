/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.ComparisonCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ComparisonCheckImpl extends AbstractCheck implements ComparisonCheck {

	@XmlAttribute(name = "lt")
	private String lessThanExpression;

	@XmlAttribute(name = "lte")
	private String lessThanOrEqualsExpression;

	@XmlAttribute(name = "gt")
	private String greaterThanExpression;

	@XmlAttribute(name = "gte")
	private String greaterThanOrEqualsExpression;

	@XmlAttribute(name = "eq")
	private String equalsExpression;

	@Override
	public String getLessThanExpression() {
		return this.lessThanExpression;
	}

	@Override
	public String getLessThanOrEqualsExpression() {
		return this.lessThanOrEqualsExpression;
	}

	@Override
	public String getGreaterThanExpression() {
		return this.greaterThanExpression;
	}

	@Override
	public String getGreaterThanOrEqualsExpression() {
		return this.greaterThanOrEqualsExpression;
	}

	@Override
	public String getEqualsExpression() {
		return this.equalsExpression;
	}

}
