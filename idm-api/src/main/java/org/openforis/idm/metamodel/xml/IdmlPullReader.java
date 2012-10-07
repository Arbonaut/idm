package org.openforis.idm.metamodel.xml;

import static org.openforis.idm.metamodel.xml.IdmlParser.IDML3_NS_URI;

import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
public abstract class IdmlPullReader extends XmlPullReader {
	
	IdmlPullReader(String tagName) {
		super(IDML3_NS_URI, tagName);
	}
	
	IdmlPullReader(String tagName, Integer maxCount) {
		super(IDML3_NS_URI, tagName, maxCount);
	}

	public Survey getSurvey() {
		XmlPullReader parent = getParentReader();
		while ( parent != null ) {
			if ( parent instanceof SurveyPullReader ) {
				return ((SurveyPullReader) parent).getSurvey();
			}
			parent = parent.getParentReader();
		}
		return null;
	}
	
	// HELPER METHODS

	protected Boolean getBooleanAttribute(String attr, boolean required) throws XmlParseException {
		String val = getAttribute(attr, required);
		return val == null ? null : Boolean.valueOf(val);
	}

	protected Integer getIntegerAttribute(String attr, boolean required) throws XmlParseException {
		String val = getAttribute(attr, required);
		return val == null ? null : Integer.valueOf(val);
	}

	protected Float getFloatAttribute(String attr, boolean required) throws XmlParseException {
		String val = getAttribute(attr, required);
		return val == null ? null : Float.valueOf(val);
	}

	// TODO namespace in kXML must be null for this to work, even though the attributes 
	// are in IDML namespace.  how does this behave with other xmlpull implementations?
	protected String getAttribute(String attr, boolean required) throws XmlParseException {
		XmlPullParser parser = getParser();
		String value = parser.getAttributeValue(null, attr);
		if ( required && (value == null || value.isEmpty())  ) {
			throw new XmlParseException(parser, "missing required attribute "+attr);
		}
		return value;
	}
}