/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public abstract class Check implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Flag {
		ERROR, WARN
	}

	@XmlAttribute(name = "flag")
	private Flag flag;

	@XmlAttribute(name = "if")
	private String condition;

	@XmlElement(name = "message", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> messages;

	public Flag getFlag() {
		return this.flag;
	}

	public String getCondition() {
		return this.condition;
	}

	public List<LanguageSpecificText> getMessages() {
		return Collections.unmodifiableList(this.messages);
	}
}
