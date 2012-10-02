package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author G. Miceli
 */
public class SaxSurveyUnmarshaller {
	public static void main(String[] args) throws SAXException {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		SurveyContext surveyContext = new DefaultSurveyContext();
		SurveyHandler handler = new SurveyHandler(surveyContext);
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		Survey survey = handler.getSurvey();
		System.out.println(survey);
	}
}
