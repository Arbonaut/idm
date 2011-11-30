/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.PatternCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class PatternCheckImpl extends AbstractExplicitCheck implements PatternCheck {

	@XmlAttribute(name = "regex")
	private String regularExpression;

	@Override
	public String getRegularExpression() {
		return this.regularExpression;
	}

	@Override
	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}
}
