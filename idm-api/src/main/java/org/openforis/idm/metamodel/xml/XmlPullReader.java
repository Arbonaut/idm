package org.openforis.idm.metamodel.xml;

import static org.xmlpull.v1.XmlPullParser.END_TAG;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class XmlPullReader {
	public static final String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";
	
	private String tagName;
	private String namespace;
	private XmlPullReader[] childPullReaders;
	private int lastChildPullReaderIdx;
	private int count;
	private Integer maxCount;
	private boolean unordered;
	private XmlPullReader parentReader;

	public XmlPullReader(String namespace, String tagName) {
		this(namespace, tagName, null);
	}
	
	public XmlPullReader(String namespace, String tagName, Integer maxCount) {
		this.namespace = namespace;
		this.tagName = tagName;
		this.maxCount = maxCount;
		this.unordered = true;
		reset();
	}

	protected void setChildPullReaders(XmlPullReader... childTagReaders) {
		this.childPullReaders = childTagReaders;
		for (XmlPullReader reader : childTagReaders) {
			reader.setParentReader(this);
		}

	}
	
	protected XmlPullReader getParentReader() {
		return parentReader;
	}
	
	private void setParentReader(XmlPullReader xmlPullReader) {
		this.parentReader = xmlPullReader;
	}

	public void parseElement(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		if ( !isTagSupported(parser) ) {
		    try {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
				    throw new IllegalStateException("Invalid tag for this reader");
				}
			} catch (XmlPullParserException e) {
				throw new XmlParseException(e);
			}
		}
		
		this.count++;

		if ( maxCount != null && count > maxCount ) {
			throw new XmlParseException(parser, "Maximum number of instances exceeded");
		}
		
		boolean done = onStartTag(parser);
		
		if ( !done ) {
			handleTagContents(parser);
		}
		
		onEndTag(parser);
		
		this.lastChildPullReaderIdx = 0;
		resetChildReaders();
	}

	protected void onEndTag(XmlPullParser parser) throws XmlParseException {
		// no-op
	}
	
	protected void handleTagContents(XmlPullParser parser)
			throws XmlPullParserException, IOException, XmlParseException {
		while ( parser.nextTag() != END_TAG ) {
			XmlPullReader childTagReader = getChildTagReader(parser);
			if ( childTagReader == this ) {
				// When recursing, store state and reset the 0
				int tmpLastChildPullReaderIdx = lastChildPullReaderIdx;
				this.lastChildPullReaderIdx = 0;
				int tmpCount = count;
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
	public int getCount() {
		return count;
	}
	
	public Integer getMaxCount() {
		return maxCount;
	}
	
	public void reset() {
		this.lastChildPullReaderIdx = 0;
		this.count = 0;
	}
	
	protected boolean onStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		// no-op
		return false;
	}
	
	public boolean isTagSupported(XmlPullParser parser) {
		return tagName.equals(parser.getName()) && namespace.equals(parser.getNamespace()); 
	}
	
	protected void skip(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
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
	
	protected XmlPullReader getChildTagReader(XmlPullParser parser) throws XmlParseException {
		if ( childPullReaders != null ) {
			for (int i = lastChildPullReaderIdx; i < childPullReaders.length; i++) {
				XmlPullReader tagReader = childPullReaders[i];
				if ( tagReader.isTagSupported(parser) ) {
					if ( !unordered ) {
						this.lastChildPullReaderIdx = i;
					}
					return tagReader;
				} 
			}
		}
		throw new XmlParseException(parser);
	}

	protected boolean isUnordered() {
		return unordered;
	}

	protected void setUnordered(boolean unordered) {
		this.unordered = unordered;
	}
}
