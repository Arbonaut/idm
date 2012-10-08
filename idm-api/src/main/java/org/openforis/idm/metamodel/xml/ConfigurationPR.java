package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class ConfigurationPR extends IdmlPullReader {
	public ConfigurationPR() {
		super("configuration");
	}

	// TODO Config adapter
	@Override
	protected void onStartTag()
			throws XmlParseException, XmlPullParserException, IOException {
		String config = readEntireTag();
		System.out.println(config);
	}
}