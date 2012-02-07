/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"language", "text"})
public class LanguageSpecificText implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace", name = "lang")
	private String language;

	@XmlValue
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String text;

	public LanguageSpecificText() {
		super();
	}

	public LanguageSpecificText(String language, String text) {
		this.language = language;
		this.text = text;
	}

	public String getLanguage() {
		return this.language;
	}

	protected void setLanguage(String language) {
		this.language = language;
	}
	
	public String getText() {
		return this.text;
	}
	
	protected void setText(String text) {
		this.text = text;
	}
}
