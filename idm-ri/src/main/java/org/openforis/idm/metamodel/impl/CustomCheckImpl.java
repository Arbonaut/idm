/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CustomCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CustomCheckImpl extends AbstractCheck implements CustomCheck {

	@XmlAttribute(name = "expr")
	private String expression;

	@Override
	public String getExpression() {
		return this.expression;
	}

}
