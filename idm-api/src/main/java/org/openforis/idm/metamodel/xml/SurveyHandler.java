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
public class SurveyHandler extends DefaultHandler {
//	private static final String XML_SCHEMA_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema";
	private static final String IDML3_NAMESPACE_URI = "http://www.openforis.org/idml/3.0";

	private Stack<ContentHandler> handlerStack;

	private SurveyContext surveyContext;
	
	private Survey survey;
	
	private boolean started;
	
	private boolean ended;
	
	public SurveyHandler(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
		this.started = false;
		this.ended = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ( started ) {
			ContentHandler handler = getContentHandler();
			handler.startElement(uri, localName, qName, attributes);
		} else {
			if ( !IDML3_NAMESPACE_URI.equals(uri) ) {
				throw new SAXException("Invalid namespace "+uri);
			}
			if ( localName.equals("survey") ) {
				this.survey = new Survey(surveyContext);
			} else {
				throw new SAXException("Unexpected element "+localName);
			}
			
			this.started = true;
		}
	}

	protected ContentHandler getContentHandler() {
		return handlerStack.peek();
	}

	public Survey getSurvey() {
		return ended ? null : survey;
	}
}
