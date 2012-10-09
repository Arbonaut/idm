package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class ConfigurationPR extends IdmlPullReader {
	public ConfigurationPR() {
		super("configuration");
	}

	// TODO Config adapter
	@Override
	protected void onStartTag()
			throws XmlParseException, XmlPullParserException, IOException {
		String body = nextElement(false);
		SurveyUnmarshaller unmarshaller = getSurveyUnmarshaller();
		ConfigurationUnmarshaller<? extends Configuration> configUnmarshaller = unmarshaller.getConfigurationUnmarshaller();
		Configuration config = configUnmarshaller.unmarshal(body);
		Survey survey = getSurvey();
		survey.addConfiguration(config);
		System.out.println(survey.getConfiguration());
	}
}