/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.AttributeDefault;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class AttributeDefaultImpl implements AttributeDefault {

	@XmlAttribute(name = "expr")
	private String expression;

	@XmlAttribute(name = "if")
	private String condition;

	@Override
	public String getExpression() {
		return this.expression;
	}

	@Override
	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String getCondition() {
		return this.condition;
	}

	@Override
	public void setCondition(String condition) {
		this.condition = condition;
	}

}
