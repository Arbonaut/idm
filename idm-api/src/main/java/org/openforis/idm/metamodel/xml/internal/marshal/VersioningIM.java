package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;

/**
 * 
 * @author G. Miceli
 *
 */
class VersioningIM extends AbstractIdmlMarshaller<ModelVersion, Survey> {

	VersioningIM() {
		super(VERSION);
		setListWrapperTag(VERSIONING);
		addChildMarshallers(new LabelIM(), new DescriptionIM(), new DateIM());
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		List<ModelVersion> versions = survey.getVersions();
		marshal(versions);
	}
	
	@Override
	protected void attributes(ModelVersion version) throws IOException {
		attribute(ID, version.getId());
		attribute(NAME, version.getName());
	}
	
	private class LabelIM extends LanguageSpecificTextIM<ModelVersion> {

		public LabelIM() {
			super(LABEL);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getLabels());
		}
	}
	
	private class DescriptionIM extends LanguageSpecificTextIM<ModelVersion> {

		public DescriptionIM() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getDescriptions());
		}
	}
	
	private class DateIM extends TextIM<ModelVersion> {

		public DateIM() {
			super(DATE);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getDate());
		}
	}
}
