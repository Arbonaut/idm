/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ValueCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class AbstractValueCheck extends AbstractCheck implements ValueCheck {

	@XmlTransient
	private Flag flag;

	@XmlAttribute(name = "if")
	private String condition;

	@XmlElement(name = "message", type = LanguageSpecificTextImpl.class)
	List<LanguageSpecificText> messages;

	@Override
	public Flag getFlag() {
		return this.flag;
	}

	@Override
	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	@Override
	public String getCondition() {
		return this.condition;
	}

	@Override
	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public List<LanguageSpecificText> getMessages() {
		return this.messages;
	}

	@Override
	public void setMessages(List<LanguageSpecificText> messages) {
		this.messages = messages;
	}

}
