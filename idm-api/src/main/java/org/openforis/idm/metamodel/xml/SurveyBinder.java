package org.openforis.idm.metamodel.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveyBinder {
	private SurveyContext surveyContext;
	private List<ApplicationOptionsBinder<?>> optionsBinders;

	public SurveyBinder(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
		this.optionsBinders = new ArrayList<ApplicationOptionsBinder<?>>();
	}

	// TODO Remove
	public static void main(String[] args) {
		try {
			File f = new File("../idm-test/src/main/resources/test.idm.xml");
			InputStream is = new FileInputStream(f);
			SurveyContext ctx = new DefaultSurveyContext();
			
			SurveyBinder unmarshaller = new SurveyBinder(ctx); 
			unmarshaller.unmarshal(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addApplicationOptionsBinder(ApplicationOptionsBinder<?> binder) {
		optionsBinders.add(binder);
	}

	/**
	 * 
	 * @param type
	 * @return the first binder which supports the specified type, or null
	 * if none found
	 */
	ApplicationOptionsBinder<?> getApplicationOptionsBinder(String type) {
		for (ApplicationOptionsBinder<?> binder : optionsBinders) {
			if ( binder.isSupported(type) ) {
				return binder;
			}
		}
		return null;
	}
	public SurveyContext getSurveyContext() {
		return surveyContext;
	}
	
	synchronized
	public void marshal(InputStream is, Survey survey) throws XmlParseException, IOException {
		// TODO implement
		throw new UnsupportedOperationException();
	}
		
	synchronized
	public Survey unmarshal(InputStream is) throws XmlParseException, IOException {
		XmlPullParser parser = null;
		try {
			SurveyPullReader surveyReader = new SurveyPullReader(this);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			parser.setInput(is, "UTF-8");			
			surveyReader.parse(parser);
			
			return surveyReader.getSurvey();
		} catch (XmlPullParserException e) {
			throw new XmlParseException(parser, e.getMessage(), e);
		}
	}	
}
