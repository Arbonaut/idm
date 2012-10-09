package org.openforis.idm.metamodel.xml;

import static org.xmlpull.v1.XmlPullParser.END_TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
abstract class XmlPullReader {
	public static final String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";
	
	private String tagName;
	private String namespace;
	private List<XmlPullReader> childPullReaders;
	private int lastChildPullReaderIdx;
	private int count;
	private Integer maxCount;
	private boolean unordered;
	private XmlPullReader parentReader;
	private XmlPullParser parser; 
	
	XmlPullReader(String namespace, String tagName) {
		this(namespace, tagName, null);
	}
	
	XmlPullReader(String namespace, String tagName, Integer maxCount) {
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
	
	public void parse(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		parser.nextTag();
		parseElement(parser);
	}
	
	synchronized
	public void parseElement(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		
		if ( !isTagSupported(parser.getName(), parser.getNamespace()) ) {
		    throw new IllegalStateException("Invalid tag for this reader");
		}
		
		this.parser = parser;
		
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
}
