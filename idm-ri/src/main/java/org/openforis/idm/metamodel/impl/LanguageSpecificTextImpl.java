/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class LanguageSpecificTextImpl implements LanguageSpecificText {

	@XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace", name = "lang")
	private String language;

	@XmlValue
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	private String text;

	public LanguageSpecificTextImpl() {
		super();
	}

	public LanguageSpecificTextImpl(String language, String text) {
		super();
		this.language = language;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openforis.idm.metamodel.impl.LanguageSpecificText#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return this.language;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openforis.idm.metamodel.impl.LanguageSpecificText#getText()
	 */
	@Override
	public String getText() {
		return this.text;
	}

}
