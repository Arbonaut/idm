package org.openforis.idm.metamodel.xml;

import static org.xmlpull.v1.XmlPullParser.END_TAG;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
public abstract class XmlTagReader {
	public static final String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";
	
	private String tagName;
	private String namespace;
	private XmlTagReader[] childTagReaders;
	private int idx;
	private boolean seen;

	public XmlTagReader(String namespace, String tagName) {
		this.namespace = namespace;
		this.tagName = tagName;
		reset();
	}

	protected void setChildTagReaders(XmlTagReader... childTagReaders) {
		this.childTagReaders = childTagReaders;
	}
	
	public void readTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
		if ( !isTagSupported(parser) ) {
		    try {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
				    throw new IllegalStateException("Invalid tag for this reader");
				}
			} catch (XmlPullParserException e) {
				throw new XmlParseException(e);
			}
		}
		
		this.seen = true;
		
		boolean done = handleStartTag(parser);
		
		if ( !done ) {
			while ( parser.nextTag() != END_TAG ) {
				XmlTagReader childTagReader = getChildTagReader(parser);
				childTagReader.readTag(parser);
			}
			
			// Reset child readers
			if ( childTagReaders != null ) {
				for (XmlTagReader childTagReader : childTagReaders) {
					childTagReader.reset();
				}
			}
		}
	}

	public boolean isSeen() {
		return seen;
	}
	
	public void reset() {
		this.idx = 0;
		this.seen = false;
	}
	
	protected boolean handleStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
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
	
	private XmlTagReader getChildTagReader(XmlPullParser parser) throws XmlParseException {
		if ( childTagReaders != null ) {
			for (int i = idx; i < childTagReaders.length; i++) {
				XmlTagReader tagReader = childTagReaders[i];
				if ( tagReader.isTagSupported(parser) ) {
					this.idx = i;
					return tagReader;
				} 
			}
		}
		throw new XmlParseException(parser);
	}
}
