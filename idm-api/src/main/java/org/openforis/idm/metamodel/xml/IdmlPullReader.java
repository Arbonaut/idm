package org.openforis.idm.metamodel.xml;

import static org.xmlpull.v1.XmlPullParser.*;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.namespace.QName;

import org.kxml2.io.KXmlSerializer;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author G. Miceli
 */
public abstract class IdmlPullReader extends XmlPullReader {

	private SurveyUnmarshaller unmarshaller;

	public static final String IDML3_NS_URI = "http://www.openforis.org/idml/3.0";

	IdmlPullReader(String tagName) {
		super(IDML3_NS_URI, tagName);
	}
	
	IdmlPullReader(String tagName, Integer maxCount) {
		super(IDML3_NS_URI, tagName, maxCount);
	}
	
	SurveyUnmarshaller getSurveyUnmarshaller() {
		return unmarshaller;
	}
	
	void setSurveyUnmarshaller(SurveyUnmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	protected String nextElement(boolean includeOuterTag) throws XmlParseException, XmlPullParserException, IOException {
		XmlPullParser in = getParser();
		if (in.getEventType() != START_TAG) {
		    throw new XmlParseException(in, "start tag expected");
		}
		StringWriter sw = new StringWriter(); 
		XmlSerializer out = new KXmlSerializer();
		out.setOutput(sw);
		out.startDocument("UTF-8", true);
		if ( includeOuterTag ) {
			out.startTag(in.getNamespace(), in.getName());
		}
	    int depth = 1;
	    while (depth != 0) {
	        switch (in.next()) {
	        case START_TAG:
	        	out.startTag(in.getNamespace(), in.getName());
	        	for ( int i=0; i < in.getAttributeCount(); i++) {
	        		out.attribute(in.getAttributeNamespace(i), in.getAttributeName(i), in.getAttributeValue(i));
	        	}
	            depth++;
	            break;
	        case END_TAG:
	        	if ( includeOuterTag || depth > 1 ) {
	        		out.endTag(in.getNamespace(), in.getName());
	        	}
	            depth--;
	            break;
	        case TEXT:
	        	out.text(in.getText());
	        	break;
	        case CDSECT:
	        	out.cdsect(in.getText());
	        	break;
	        case ENTITY_REF:
	        	out.entityRef(in.getText());
	        	break;
	        }
	    }
	    return sw.toString();
//	    IOUtils.copy(tpis, new OutputStreamWriter(System.out), "UTF-8");
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


	protected void handleAnnotation(QName qName, String value) {
		// no-op
	}

	@Override
	protected final void parseTag() throws XmlPullParserException, IOException,
			XmlParseException {
		super.parseTag();
		parseAnnotations();
	}

	private void parseAnnotations() {
		XmlPullParser parser = getParser();
		for (int i=0; i < parser.getAttributeCount(); i++) {
			String ns = parser.getAttributeNamespace(i);
			String prefix = parser.getAttributePrefix(i);
			if ( prefix != null && !IDML3_NS_URI.equals(ns) ) {
				String name = parser.getAttributeName(i);
				String value = parser.getAttributeValue(i);
				handleAnnotation(new QName(ns, name, prefix), value);
			}
		}
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
	
	@Override
	protected void addChildPullReaders(XmlPullReader... childTagReaders) {
		super.addChildPullReaders(childTagReaders);
		for (XmlPullReader reader : childTagReaders) {
			((IdmlPullReader) reader).setSurveyUnmarshaller(unmarshaller);
		}

	}
}