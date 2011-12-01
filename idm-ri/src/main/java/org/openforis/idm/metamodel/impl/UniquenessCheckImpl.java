/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.UniquenessCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class UniquenessCheckImpl extends AbstractExplicitCheck implements UniquenessCheck {

	@XmlAttribute(name = "expr")
	private String expression;

	@Override
	public String getExpression() {
		return this.expression;
	}

	@Override
	public void setExpression(String expression) {
		this.expression = expression;
	}

}
