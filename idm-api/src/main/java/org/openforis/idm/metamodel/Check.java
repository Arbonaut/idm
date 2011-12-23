/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public abstract class Check {

	public enum Flag {
		ERROR, WARN
	}

	@XmlAttribute(name = "flag")
	@XmlJavaTypeAdapter(value = FlagAdapter.class)
	protected Flag flag;

	@XmlAttribute(name = "if")
	private String condition;

	@XmlElement(name = "message", type = LanguageSpecificText.class)
	List<LanguageSpecificText> messages;

	public Flag getFlag() {
		return this.flag;
	}

	public String getCondition() {
		return this.condition;
	}

	public List<LanguageSpecificText> getMessages() {
		return Collections.unmodifiableList(this.messages);
	}

	private static class FlagAdapter extends XmlAdapter<String, Flag> {

		@Override
		public Flag unmarshal(String v) throws Exception {
			return v==null ? null : Flag.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Flag v) throws Exception {
			return v==null ? null : v.toString().toLowerCase();
		}
	}
}
