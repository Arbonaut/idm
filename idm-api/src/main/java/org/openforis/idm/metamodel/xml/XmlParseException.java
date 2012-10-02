package org.openforis.idm.metamodel.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public class XmlParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public XmlParseException(Throwable cause) {
		super(cause);
	}

	public XmlParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlParseException(String message) {
		super(message);
	}

	public XmlParseException(XmlPullParser parser) {
		this(parser, "unexpected");
	}

	public XmlParseException(XmlPullParser parser, String msg) {
		super(msg+" "+parser.getPositionDescription());
	}

}
