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
class VersioningXS extends XmlSerializerSupport<ModelVersion, Survey> {

	VersioningXS() {
		super(VERSION);
		setListWrapperTag(VERSIONING);
		addChildMarshallers(new LabelXS(), new DescriptionXS(), new DateXS());
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
	
	private class LabelXS extends LanguageSpecificTextXS<ModelVersion> {

		public LabelXS() {
			super(LABEL);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getLabels());
		}
	}
	
	private class DescriptionXS extends LanguageSpecificTextXS<ModelVersion> {

		public DescriptionXS() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getDescriptions());
		}
	}
	
	private class DateXS extends TextXS<ModelVersion> {

		public DateXS() {
			super(DATE);
		}
		
		@Override
		protected void marshalInstances(ModelVersion version) throws IOException {
			marshal(version.getDate());
		}
	}
}
