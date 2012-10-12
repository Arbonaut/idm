package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.xmlpull.v1.XmlPullParser.CDSECT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.ENTITY_REF;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author G. Miceli
 */
public abstract class XmlPullReader {
	
	private String tagName;
	private String namespace;
	private List<XmlPullReader> childPullReaders;
	private int lastChildPullReaderIdx;
	private int count;
	private Integer maxCount;
	private boolean unordered;
	private XmlPullReader parentReader;
	private XmlPullParser parser; 
	
	protected XmlPullReader(String namespace, String tagName) {
		this(namespace, tagName, null);
	}
	
	protected XmlPullReader(String namespace, String tagName, Integer maxCount) {
		this.namespace = namespace;
		this.tagName = tagName;
		this.maxCount = maxCount;
		this.unordered = true;
		reset();
	}

	protected void addChildPullReaders(XmlPullReader... childTagReaders) {
		if ( childPullReaders == null ) {
			this.childPullReaders = new ArrayList<XmlPullReader>();
		}
		for (XmlPullReader reader : childTagReaders) {
			childPullReaders.add(reader);
			reader.setParentReader(this);
		}
	}
	
	protected XmlPullReader getParentReader() {
		return parentReader;
	}
	
	private void setParentReader(XmlPullReader xmlPullReader) {
		this.parentReader = xmlPullReader;
	}

	protected XmlPullParser getParser() {
		return parser;
	}
	
	public String getTagName() {
		return tagName;
	}

	synchronized
	protected void parse(XmlPullParser parser) throws XmlParseException, IOException {
		try {
			parser.nextTag();
			parseElement(parser);
		} catch (XmlPullParserException e) {
			throw new XmlParseException(parser, e.getMessage());
		}
	}
	
	synchronized
	public void parse(InputStream is, String enc) throws XmlParseException, IOException {
		try {
			XmlPullParser parser = createParser();
			parser.setInput(is, enc);			
			parse(parser);
		} catch (XmlPullParserException e) {
			throw new XmlParseException(getParser(), e.getMessage());
		}
	}
	
	synchronized
	public void parse(Reader reader) throws XmlParseException, IOException {
		try {
			XmlPullParser parser = createParser();
			parser.setInput(reader);
			parse(parser);
		} catch (XmlPullParserException e) {
			throw new XmlParseException(getParser(), e.getMessage());
		}
	}

	private XmlPullParser createParser() throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		return parser;
	}

	private void parseElement(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		this.parser = parser;
		
		if ( !isTagSupported(parser.getName(), parser.getNamespace()) ) {
		    throw new IllegalStateException("Invalid tag for this reader");
		}
		
		this.count++;

		if ( maxCount != null && count > maxCount ) {
			throw new XmlParseException(parser, "Too many elements; max "+maxCount);
		}

		parseTag();
		
		this.lastChildPullReaderIdx = 0;
		resetChildReaders();
	}

	protected void parseTag() throws XmlParseException, XmlPullParserException, IOException {
		onStartTag();
		
		parseTagBody();
		
		onEndTag();
	}

	protected void parseTagBody()
			throws XmlPullParserException, IOException, XmlParseException {
		if ( parser.getEventType() != END_TAG ) {
			while ( parser.nextTag() != END_TAG ) {
				XmlPullReader childTagReader = getChildPullReader();
				handleChildTag(childTagReader);
			}
		}
	}
	
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		// When recursing, store state and reset the 0
		int tmpLastChildPullReaderIdx = lastChildPullReaderIdx;
		int tmpCount = count;
		this.lastChildPullReaderIdx = 0;
		this.count = 0;
		// Recurse child node
		childTagReader.parseElement(parser);
		// Restore state from before iteration
		this.lastChildPullReaderIdx = tmpLastChildPullReaderIdx;
		this.count = tmpCount;
	}

	protected void onEndTag() throws XmlParseException {
		// no-op
	}

	protected void resetChildReaders() {
		if ( childPullReaders != null ) {
			for (XmlPullReader childTagReader : childPullReaders) {
				childTagReader.reset();
			}
		}
	}

	/**
	 * @return number of times element is repeated
	 */
	int getCount() {
		return count;
	}
	
	Integer getMaxCount() {
		return maxCount;
	}
	
	void reset() {
		this.lastChildPullReaderIdx = 0;
		this.count = 0;
	}
	
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		// no-op
	}
	
	public boolean isTagSupported(String tag, String ns) {
		return tagName.equals(tag) && namespace.equals(ns); 
	}
	
	protected List<XmlPullReader> getChildPullReaders() {
		return childPullReaders;
	}
	
	protected XmlPullReader getChildPullReader() throws XmlParseException {
		if ( childPullReaders != null ) {
			for (int i = lastChildPullReaderIdx; i < childPullReaders.size(); i++) {
				XmlPullReader tagReader = childPullReaders.get(i);
				if ( tagReader.isTagSupported(parser.getName(), parser.getNamespace()) ) {
					if ( !unordered ) {
						this.lastChildPullReaderIdx = i;
					}
					return tagReader;
				} 
			}
		}
		throw new XmlParseException(parser, "unsupported tag");
	}
	
	protected boolean isUnordered() {
		return unordered;
	}

	protected void setUnordered(boolean unordered) {
		this.unordered = unordered;
	}
	
	protected String nextElement(boolean includeOuterTag) throws XmlParseException, XmlPullParserException, IOException {
		XmlPullParser in = getParser();
		if (in.getEventType() != START_TAG) {
		    throw new XmlParseException(in, "start tag expected");
		}
		StringWriter sw = new StringWriter(); 
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlSerializer out = factory.newSerializer();
		out.setOutput(sw);
//		out.startDocument("UTF-8", true);
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
