package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
abstract class LanguageSpecificTextPR extends IdmlPullReader {
	private boolean requireType;
	
	public LanguageSpecificTextPR(String tagName, boolean requireType) {
		super(tagName);
		this.requireType = requireType;
	}
	
	public LanguageSpecificTextPR(String tagName) {
		this(tagName, false);
	}

	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		XmlPullParser parser = getParser();
		String lang = parser.getAttributeValue(XML_NS_URI, XML_LANG_ATTRIBUTE);
		String type = getAttribute(TYPE, requireType);
		String text = parser.nextText();
		processText(lang, type, text);
	}

	/** 
	 * Override this method to handle "type" attribute for other label types
	 * @param lang
	 * @param type
	 * @param text
	 * @throws XmlParseException 
	 */
	protected void processText(String lang, String type, String text) throws XmlParseException {
		LanguageSpecificText lst = new LanguageSpecificText(lang, text.trim());
		processText(lst);
	}
	
	protected void processText(LanguageSpecificText lst) {
		// no-op
	}
}