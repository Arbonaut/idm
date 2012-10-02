package org.openforis.idm.metamodel.xml;

import java.util.Stack;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author G. Miceli
 */
public abstract class NestedContentHandler extends DefaultHandler {
//	private static final String XML_SCHEMA_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema";
//	private static final String IDML3_NAMESPACE_URI = "http://www.openforis.org/idml/3.0";

	private boolean started;
	private boolean ended;
	private NestedContentHandler[] childHandlers;
	
	public NestedContentHandler() {
		this.started = false;
		this.ended = false;
	}
	
	public NestedContentHandler(NestedContentHandler[] childHandlers) {
		this.childHandlers = childHandlers;
		this.started = false;
		this.ended = false;
	}
	
	protected abstract boolean isElementSupported(String uri, String localName);

	protected abstract void processStartElement(String uri, String localName, String qName, Attributes attributes);
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ( started ) {
			if ( childHandlers != null ) {
				for (NestedContentHandler handler : childHandlers) {
					if ( handler.isElementSupported(uri, localName) ) {
						handler.startElement(uri, localName, qName, attributes);
						return;
					}
				}
			}
			throw new SAXException("Unexpected element name or namespace "+qName);
		} else {
			if ( isElementSupported(uri, localName) ) {
				processStartElement(uri, localName, qName, attributes);
			} else {
				throw new SAXException("Unexpected element name or namespace "+qName);
			}
			this.started = true;
			this.ended = false;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		this.ended = true;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public boolean isEnded() {
		return ended;
	}
}
