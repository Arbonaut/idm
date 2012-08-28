/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.List;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;*/
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.convert.Convert;

import org.openforis.idm.metamodel.xml.internal.CollapsedStringAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes="", elements = {"language", "text"})
public class LanguageSpecificText implements Serializable {

	private static final long serialVersionUID = 1L;

	@Namespace(reference = "http://www.w3.org/XML/1998/namespace") 
	@Attribute(/*namespace = "http://www.w3.org/XML/1998/namespace", */name = "lang")
	private String language;

	//@XmlValue
	@Text(required=false)
	@Convert(CollapsedStringAdapter.class)
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
	
	public static LanguageSpecificText getByLanguage(List<? extends LanguageSpecificText> list, String language) {
		for (LanguageSpecificText lst : list) {
			if ( lst.getLanguage() != null && lst.getLanguage().equals(language) || 
					lst.getLanguage() == null && language == null) {
				return lst;
			}
		}
		return null;
	}
	
	public static String getText(List<? extends LanguageSpecificText> list, String language) {
		LanguageSpecificText languageSpecificText = getByLanguage(list, language);
		if ( languageSpecificText != null ) {
			return languageSpecificText.getText();
		} else {
			return null;
		}
	}
	
	public static void setText(List<LanguageSpecificText> list, String language, String text) {
		LanguageSpecificText languageSpecificText = new LanguageSpecificText(language, text);
		LanguageSpecificText oldLanguageSpecificText = getByLanguage(list, language);
		if ( oldLanguageSpecificText == null ) {
			list.add(languageSpecificText);
		} else {
			int index = list.indexOf(oldLanguageSpecificText);
			list.set(index, languageSpecificText);
		}
	}
	
	public static void remove(List<? extends LanguageSpecificText> list, String language) {
		LanguageSpecificText languageSpecificText = getByLanguage(list, language);
		if ( languageSpecificText != null ) {
			list.remove(languageSpecificText);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LanguageSpecificText other = (LanguageSpecificText) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
}
