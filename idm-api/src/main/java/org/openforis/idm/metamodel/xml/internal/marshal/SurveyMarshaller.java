package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;


/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveyMarshaller extends AbstractIdmlMarshaller<Survey, Void>{

	public SurveyMarshaller(SurveyIdmlBinder binder) {
		super(IDML3_NAMESPACE_URI, SURVEY);
		addChildMarshallers(
//				new ProjectIM(),
//				new UriIM(), 
//				new CycleIM(),
//				new DescriptionIM(),
//				new LanguageIM(),
//				new ApplicationOptionsIM(binder),
//				new VersioningIM(), 
//				new CodeListsIM(),
//				new UnitsIM(),
//				new SpatialReferenceSystemsIM(),
				new SchemaIM());
	}

	@Override
	protected void start(Survey survey) throws IOException {
		startDocument();
		setDefaultNamespace(IDML3_NAMESPACE_URI);
		super.start(survey);
	}

	@Override
	protected void attributes(Survey survey) throws IOException {
		attribute(LAST_ID, survey.getLastId());
		attribute(PUBLISHED, survey.isPublished() ? true : null);
	}
	
	@Override
	protected void end(Survey survey) throws IOException {
		super.end(survey);
		getXmlSerializer().endDocument();
	}

	private class ProjectIM extends LanguageSpecificTextIM<Survey> {
		public ProjectIM() {
			super(PROJECT);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getProjectNames());
		}
	}

	private class UriIM extends TextIM<Survey> {
		public UriIM() {
			super(URI);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getUri());
		}
	}

	private class CycleIM extends TextIM<Survey> {
		public CycleIM() {
			super(CYCLE);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getCycle());
		}
	}

	private class DescriptionIM extends LanguageSpecificTextIM<Survey> {
		public DescriptionIM() {
			super(DESCRIPTION);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getDescriptions());
		}
	}

	private class LanguageIM extends TextIM<Survey> {
		public LanguageIM() {
			super(LANGUAGE);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getLanguages());
		}
	}
}
