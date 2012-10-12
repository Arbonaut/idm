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
class ApplicationOptionsIM extends AbstractIdmlMarshaller<ApplicationOptions, Survey> {

	private SurveyIdmlBinder binder;

	ApplicationOptionsIM(SurveyIdmlBinder binder) {
		super(OPTIONS);
		setListWrapperTag(APPLICATION_OPTIONS);
		this.binder = binder;
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		List<ApplicationOptions> options = survey.getApplicationOptions();
		marshal(options);
	}
	
	@Override
	protected void attributes(ApplicationOptions options) throws IOException {
		String type = options.getType();
		attribute(TYPE, type);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void body(ApplicationOptions options) throws IOException {
		String type = options.getType();
		ApplicationOptionsBinder optionsBinder = binder.getApplicationOptionsBinder(type);
		String xml = optionsBinder.marshal(options);
		writeXml(xml);
	}
}
