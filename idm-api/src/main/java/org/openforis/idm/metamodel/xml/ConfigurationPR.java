package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class ConfigurationPR extends IdmlPullReader {
	public ConfigurationPR() {
		super("configuration");
	}

	@Override
	protected boolean onStartTag(XmlPullParser parser)
			throws XmlParseException, XmlPullParserException, IOException {
		skip(parser);
		return true;
	}
}