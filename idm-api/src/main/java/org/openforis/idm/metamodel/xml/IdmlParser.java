package org.openforis.idm.metamodel.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author G. Miceli
 */
public class IdmlParser {
	private SurveyContext surveyContext;
	
	public IdmlParser(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
	}

	// TODO Remove
	public static void main(String[] args) {
		try {
			File f = new File("../idm-test/src/main/resources/test.idm.xml");
			InputStream is = new FileInputStream(f);
			SurveyContext ctx = new DefaultSurveyContext();
			IdmlParser unmarshaller = new IdmlParser(ctx); 
			unmarshaller.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SurveyContext getSurveyContext() {
		return surveyContext;
	}
	
	synchronized
	public Survey parse(InputStream is) throws XmlParseException, IOException {
		XmlPullParser parser = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			parser.setInput(is, "UTF-8");
			SurveyPullReader surveyReader = new SurveyPullReader(this);
			surveyReader.parse(parser);
			
			return surveyReader.getSurvey();
		} catch (XmlPullParserException e) {
			throw new XmlParseException(parser, e.getMessage(), e);
		}
	}	
}
