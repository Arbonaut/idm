package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.InputStream;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author G. Miceli
 */
public class IdmlParser {
	public static final String IDML3_NS_URI = "http://www.openforis.org/idml/3.0";
	
	private SurveyContext surveyContext;
	
	public IdmlParser(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
	}

	// TODO Remove
//	public static void main(String[] args) {
//		try {
//			File f = new File("../idm-test/src/main/resources/test.idm.xml");
//			InputStream is = new FileInputStream(f);
//			SurveyContext ctx = new DefaultSurveyContext();
//			IdmlParser unmarshaller = new IdmlParser(ctx); 
//			unmarshaller.parse(is);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	synchronized
	public Survey parse(InputStream is) throws XmlParseException, IOException {
		XmlPullParser parser = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			parser.setInput(is, null);
			SurveyPullReader tagReader = new SurveyPullReader(surveyContext);
			parser.nextTag();
			tagReader.parseElement(parser);
			
			return tagReader.getSurvey();
		} catch (XmlPullParserException e) {
			throw new XmlParseException(parser, e.getMessage(), e);
		}
	}	
}
