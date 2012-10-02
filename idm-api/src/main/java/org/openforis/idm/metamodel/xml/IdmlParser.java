package org.openforis.idm.metamodel.xml;

import java.io.File;
import java.io.FileInputStream;
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
	public static final String IDML3_NS_URI = "http://www.openforis.org/idml/3.0";
	
	private SurveyContext surveyContext;
	
	protected IdmlParser(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
	}

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

	synchronized
	private Survey parse(InputStream is) throws XmlParseException {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			parser.setInput(is, null);
			SurveyTagReader tagReader = new SurveyTagReader(surveyContext);
			tagReader.readTag(parser);
			
			return tagReader.getSurvey();
		} catch (XmlPullParserException e) {
			throw new XmlParseException(e);
		}
	}
	
	
}
