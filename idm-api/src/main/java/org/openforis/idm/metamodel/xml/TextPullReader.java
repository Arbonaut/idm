package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class TextPullReader extends IdmlPullReader {
	private boolean trimWhitespace;

	public TextPullReader(String tagName) {
		super(tagName);
		this.trimWhitespace = true;
	}

	public TextPullReader(String tagName, Integer maxCount) {
		super(tagName, maxCount);
	}
	
	protected boolean isTrim() {
		return trimWhitespace;
	}

	protected void setTrimWhitespace(boolean trim) {
		this.trimWhitespace = trim;
	}

	@Override
	public boolean onStartTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		String text = parser.nextText();
		processText(trimWhitespace ? text.trim() : text);
		return true;
	}
	
	public abstract void processText(String text);
}