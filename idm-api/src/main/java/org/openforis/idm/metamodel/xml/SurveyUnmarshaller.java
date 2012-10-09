package org.openforis.idm.metamodel.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.DefaultSurveyContext;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.internal.DefaultConfigurationUnmarshaller;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveyUnmarshaller {
	private SurveyContext surveyContext;
	private ConfigurationUnmarshaller<? extends Configuration> configUnmarshaller;
	private SurveyPullReader surveyReader;

	public SurveyUnmarshaller(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
		this.configUnmarshaller = DefaultConfigurationUnmarshaller.getInstance();
		this.surveyReader = new SurveyPullReader(this);
	}

	// TODO Remove
	public static void main(String[] args) {
		try {
			File f = new File("../idm-test/src/main/resources/test.idm.xml");
			InputStream is = new FileInputStream(f);
			SurveyContext ctx = new DefaultSurveyContext();
			
			SurveyUnmarshaller unmarshaller = new SurveyUnmarshaller(ctx); 
			unmarshaller.unmarshal(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConfigurationUnmarshaller(ConfigurationUnmarshaller<? extends Configuration> configUnmarshaller) {
		this.configUnmarshaller = configUnmarshaller;
	}

	public ConfigurationUnmarshaller<? extends Configuration> getConfigurationUnmarshaller() {
		return configUnmarshaller;
	}
	public SurveyContext getSurveyContext() {
		return surveyContext;
	}
	
	synchronized
	public Survey unmarshal(InputStream is) throws XmlParseException, IOException {
		XmlPullParser parser = null;
		try {
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
