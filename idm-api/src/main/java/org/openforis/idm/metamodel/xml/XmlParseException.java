package org.openforis.idm.metamodel.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public class XmlParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public XmlParseException(XmlPullParser parser, String message, Throwable cause) {
		super(message(parser, message), cause);
	}
	
	public XmlParseException(XmlPullParser parser, String msg) {
		super(message(parser, msg));
	}
	
	private static String message(XmlPullParser parser, String message) {
		if ( parser == null ) {
			return message;
		} else {
			return message+" "+parser.getPositionDescription();
		}
	}
}
