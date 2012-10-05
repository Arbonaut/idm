package org.openforis.idm.metamodel.xml;

import static org.openforis.idm.metamodel.xml.IdmlParser.IDML3_NS_URI;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public abstract class IdmlPullReader extends XmlPullReader {
	public IdmlPullReader(String tagName) {
		super(IDML3_NS_URI, tagName);
	}
	
	public IdmlPullReader(String tagName, Integer maxCount) {
		super(IDML3_NS_URI, tagName, maxCount);
	}
	
	// HELPER METHODS

	protected Boolean getBooleanAttribute(XmlPullParser parser, String attr, boolean required) throws XmlParseException {
		String val = getAttribute(parser, attr, required);
		return val == null ? null : Boolean.valueOf(val);
	}

	protected Integer getIntegerAttribute(XmlPullParser parser, String attr, boolean required) throws XmlParseException {
		String val = getAttribute(parser, attr, required);
		return val == null ? null : Integer.valueOf(val);
	}

	protected Float getFloatAttribute(XmlPullParser parser, String attr, boolean required) throws XmlParseException {
		String val = getAttribute(parser, attr, required);
		return val == null ? null : Float.valueOf(val);
	}

	// TODO namespace in kXML must be null for this to work, even though the attributes 
	// are in IDML namespace.  how does this behave with other xmlpull implementation  
	protected String getAttribute(XmlPullParser parser, String attr, boolean required) throws XmlParseException {
		String value = parser.getAttributeValue(null, attr);
		if ( required && (value == null || value.isEmpty())  ) {
			throw new XmlParseException(parser, "missing required attribute "+attr);
		}
		return value;
	}
}