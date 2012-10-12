package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.ApplicationOptions;
import org.openforis.idm.metamodel.Survey;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import org.openforis.idm.metamodel.xml.ApplicationOptionsBinder;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;

/**
 * 
 * @author G. Miceli
 *
 */
class ApplicationOptionsIM extends IdmlWrapperTagMarshaller<Survey> {

	private SurveyIdmlBinder binder;

	@SuppressWarnings("unchecked")
	ApplicationOptionsIM(SurveyIdmlBinder binder) {
		super(APPLICATION_OPTIONS);
		this.binder = binder;
		addChildMarshallers(new OptionsIM());
	}
	
	private class OptionsIM extends AbstractIdmlMarshaller<ApplicationOptions, Survey> {

		public OptionsIM() {
			super(OPTIONS);
		}
		
		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			List<ApplicationOptions> options = survey.getApplicationOptions();
			marshal(options);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void body() throws IOException {
			ApplicationOptions options = getSourceObject();
			String type = options.getType();
			attribute(TYPE, type);
			ApplicationOptionsBinder optionsBinder = binder.getApplicationOptionsBinder(type);
			String xml = optionsBinder.marshal(options);
			writeXml(xml);
		}
	}
}
