package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class LanguageSpecificTextPullReader extends IdmlPullReader {
	public LanguageSpecificTextPullReader(String tagName) {
		super(tagName);
	}

	@Override
	public boolean onStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		String lang = parser.getAttributeValue(XML_NS_URI, "lang");
		// TODO use correct namespace with other parsers?
		String type = parser.getAttributeValue(null, "type");
		String text = parser.nextText();
		processText(lang, type, text);
		return true;
	}

	/** 
	 * Override this method to handle "type" attribute for other label types
	 * @param lang
	 * @param type
	 * @param text
	 */
	protected void processText(String lang, String type, String text) {
		LanguageSpecificText lst = new LanguageSpecificText(lang, text.trim());
		processText(lst);
	}
	
	public void processText(LanguageSpecificText lst) {
		// no-op
	}
}