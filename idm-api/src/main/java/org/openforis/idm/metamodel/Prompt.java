/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.PromptTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prompt extends LanguageSpecificText {

	private static final long serialVersionUID = 1L;

	public enum Type {
		INTERVIEW, PAPER, HANDHELD, PC;
	}

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(PromptTypeAdapter.class)
	private Type type;

	public Prompt() {
	}

	public Prompt(String language, String text) {
		super(language, text);
	}

	public Prompt(Type type, String language, String text) {
		this(language, text);
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prompt other = (Prompt) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
}
