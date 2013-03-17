package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.xmlpull.v1.XmlSerializer;


/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveyMarshaller extends XmlSerializerSupport<Survey, Void>{

	public SurveyMarshaller(SurveyIdmlBinder binder) {
		super(IDML3_NAMESPACE_URI, SURVEY);
		addChildMarshallers(
				new ProjectXS(),
				new UriXS(), 
				new CycleXS(),
				new DescriptionXS(),
				new LanguageXS(),
				new ApplicationOptionsXS(binder),
				new VersioningXS(), 
				new CodeListsXS(),
				new UnitsXS(),
				new SpatialReferenceSystemsXS(),
				new SchemaXS());
	}

	@Override
	protected void start(Survey survey) throws IOException {
		startDocument();
		setDefaultNamespace(IDML3_NAMESPACE_URI);
		setCustomNamespacePrefixes(survey);
		super.start(survey);
	}

	protected void setCustomNamespacePrefixes(Survey survey) throws IOException {
		List<String> uris = survey.getCustomNamespaces();
		for (String uri : uris) {
			XmlSerializer xs = getXmlSerializer();
			String prefix = survey.getCustomNamespacePrefix(uri);
			xs.setPrefix(prefix, uri);
		}
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

	private class ProjectXS extends LanguageSpecificTextXS<Survey> {
		public ProjectXS() {
			super(PROJECT);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getProjectNames());
		}
	}

	private class UriXS extends TextXS<Survey> {
		public UriXS() {
			super(URI);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getUri());
		}
	}

	private class CycleXS extends TextXS<Survey> {
		public CycleXS() {
			super(CYCLE);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getCycle());
		}
	}

	private class DescriptionXS extends LanguageSpecificTextXS<Survey> {
		public DescriptionXS() {
			super(DESCRIPTION);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getDescriptions());
		}
	}

	private class LanguageXS extends TextXS<Survey> {
		public LanguageXS() {
			super(LANGUAGE);
		}

		@Override
		protected void marshalInstances(Survey survey) throws IOException {
			marshal(survey.getLanguages());
		}
	}
}
