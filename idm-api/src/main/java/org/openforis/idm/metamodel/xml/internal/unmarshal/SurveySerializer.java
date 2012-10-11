package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;


/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveySerializer extends AbstractXmlSerializer<Survey, Void>{

	private SurveyIdmlBinder binder;

	@SuppressWarnings("unchecked")
	public SurveySerializer(SurveyIdmlBinder binder) {
		super(IDML3_NAMESPACE_URI, SURVEY);
		this.binder = binder;
		addChildSerializers(
				new ProjectXS(),
				new UriXS(), 
				new CycleXS(),
				new DescriptionXS(),
				new LanguageXS() /*,
				new ApplicationOptionsXS(),
				new VersioningXS(), 
				new CodeListsXS(),
				new UnitsXS(),
				new SpatialReferenceSystemsXS(),
				new SchemaXS()*/);
	}

	@Override
	protected void start() throws IOException {
		startDocument();
		setDefaultNamespace(IDML3_NAMESPACE_URI);
		super.start();
	}

	@Override
	protected void attributes() throws IOException {
		Survey survey = getSourceObject();
		attribute(LAST_ID, survey.getLastId());
		attribute(PUBLISHED, survey.isPublished() ? true : null);
	}
	
	@Override
	protected void end() throws IOException {
		super.end();
		getSerializer().endDocument();
	}

	private class ProjectXS extends LanguageSpecificTextXS<Survey> {
		public ProjectXS() {
			super(PROJECT);
		}

		@Override
		protected void serializeInstances(Survey survey) throws IOException {
			serialize(survey.getProjectNames());
		}
	}

	private class UriXS extends TextXS<Survey> {
		public UriXS() {
			super(URI);
		}

		@Override
		protected void serializeInstances(Survey survey) throws IOException {
			serialize(survey.getUri());
		}
	}

	private class CycleXS extends TextXS<Survey> {
		public CycleXS() {
			super(CYCLE);
		}

		@Override
		protected void serializeInstances(Survey survey) throws IOException {
			serialize(survey.getCycle());
		}
	}

	private class DescriptionXS extends LanguageSpecificTextXS<Survey> {
		public DescriptionXS() {
			super(DESCRIPTION);
		}

		@Override
		protected void serializeInstances(Survey survey) throws IOException {
			serialize(survey.getDescriptions());
		}
	}

	private class LanguageXS extends TextXS<Survey> {
		public LanguageXS() {
			super(LANGUAGE);
		}

		@Override
		protected void serializeInstances(Survey survey) throws IOException {
			serialize(survey.getLanguages());
		}
	}
}
