package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.ApplicationOptions;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class ApplicationOptionsPR extends IdmlPullReader {
	public ApplicationOptionsPR() {
		super("applicationOptions", 1);
		addChildPullReaders(new OptionsPR());
	}

	private class OptionsPR extends IdmlPullReader {

		OptionsPR() {
			super("options");
		}

		@Override
		protected void onStartTag()
				throws XmlParseException, XmlPullParserException, IOException {
			String type = getAttribute("type", true);
			String body = nextElement(false);
			SurveyBinder binder = getSurveyBinder();
			ApplicationOptionsBinder<?> optionsBinder = binder.getApplicationOptionsBinder(type);
			if ( optionsBinder != null ) {
				ApplicationOptions options = optionsBinder.unmarshal(body);
				Survey survey = getSurvey();
				survey.setApplicationOptions(options);
			}
		}
	}

}