package org.openforis.idm.metamodel.xml;

import static org.openforis.idm.metamodel.xml.IdmlParser.IDML3_NS_URI;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class LanguageSpecificTextTagReader extends XmlTagReader {
	public LanguageSpecificTextTagReader(String tagName) {
		super(IDML3_NS_URI, tagName);
	}

	@Override
	public boolean handleStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		String lang = parser.getAttributeValue(XML_NS_URI, "lang");
		// TODO use correct namespace with other parsers?
		String type = parser.getAttributeValue(null, "type");
		String text = parser.nextText();
		processLabel(lang, type, text);
		return true;
	}

	/** 
	 * Override this method to handle "type" attribute for other label types
	 * @param lang
	 * @param type
	 * @param text
	 */
	protected void processLabel(String lang, String type, String text) {
		LanguageSpecificText lst = new LanguageSpecificText(lang, text.trim());
		processText(lst);
	}
	
	public void processText(LanguageSpecificText lst) {
		// no-op
	}
}