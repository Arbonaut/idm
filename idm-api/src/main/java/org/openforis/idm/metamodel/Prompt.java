/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"type", "lang", "text"})
public class Prompt extends LanguageSpecificText {

	public enum Type {
		PAPER, HANDHELD, PC;
	}

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = TypeAdapter.class)
	private Type type;

	public Prompt() {
	}

	/**
	 * @param language
	 * @param text
	 */
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

	private static class TypeAdapter extends XmlAdapter<String, Type> {

		@Override
		public Type unmarshal(String v) throws Exception {
			return Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Type v) throws Exception {
			return v.toString().toLowerCase();
		}
	}
}
