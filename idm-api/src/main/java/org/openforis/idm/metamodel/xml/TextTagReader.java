package org.openforis.idm.metamodel.xml;

import static org.openforis.idm.metamodel.xml.IdmlParser.IDML3_NS_URI;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class TextTagReader extends XmlTagReader {
	public TextTagReader(String tagName) {
		super(IDML3_NS_URI, tagName);
	}

	@Override
	public boolean handleStartTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		String text = parser.nextText();
		processText(text.trim());
		return true;
	}
	
	public abstract void processText(String text);
}