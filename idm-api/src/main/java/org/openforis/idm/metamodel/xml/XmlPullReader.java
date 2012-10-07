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

		boolean done = onStartTag();
		
		if ( !done ) {
			while ( parser.nextTag() != END_TAG ) {
				XmlPullReader childTagReader = getChildTagReader();
				handleTagContents(childTagReader);
			}
		}
		
		onEndTag();
		
		this.lastChildPullReaderIdx = 0;
		resetChildReaders();
	}

	protected void onEndTag() throws XmlParseException {
		// no-op
	}
	
	protected void handleTagContents(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader == this ) {
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
		} else {
			childTagReader.parseElement(parser);
		}
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
	
	protected boolean onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		// no-op
		return false;
	}
	
	public boolean isTagSupported(String tag, String ns) {
		return tagName.equals(tag) && namespace.equals(ns); 
	}
	
	protected void skip() throws XmlParseException, XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
		    throw new XmlParseException(parser, "start tag expected");
		}
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
//	        	System.out.println("end "+parser.getName());
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
//	        	System.out.println("start "+parser.getName());
	            depth++;
	            break;
	        }
	    }
	}
	
	protected XmlPullReader getChildTagReader() throws XmlParseException {
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
