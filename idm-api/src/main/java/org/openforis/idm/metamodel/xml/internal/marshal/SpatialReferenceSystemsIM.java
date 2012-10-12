package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;

/**
 * 
 * @author G. Miceli
 *
 */
class SpatialReferenceSystemsIM extends AbstractIdmlMarshaller<SpatialReferenceSystem, Survey> {

	SpatialReferenceSystemsIM() {
		super(SPATIAL_REFERENCE_SYSTEM);
		setListWrapperTag(SPATIAL_REFERENCE_SYSTEMS);
		addChildMarshallers(
				new LabelIM(),
				new DescriptionIM(),
				new WktIM());
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		List<SpatialReferenceSystem> srss = survey.getSpatialReferenceSystems();
		marshal(srss);
	}
	
	@Override
	protected void attributes(SpatialReferenceSystem srs) throws IOException {
		attribute(ID, srs.getId());
	}
	
	private class LabelIM extends LanguageSpecificTextIM<SpatialReferenceSystem> {

		public LabelIM() {
			super(LABEL);
		}
		
		@Override
		protected void marshalInstances(SpatialReferenceSystem srs) throws IOException {
			marshal(srs.getLabels());
		}
	}
	
	private class DescriptionIM extends LanguageSpecificTextIM<SpatialReferenceSystem> {

		public DescriptionIM() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(SpatialReferenceSystem srs) throws IOException {
			marshal(srs.getDescriptions());
		}
	}
	
	private class WktIM extends AbstractIdmlMarshaller<String, SpatialReferenceSystem> {

		public WktIM() {
			super(WKT);
		}
		
		@Override
		protected void marshalInstances(SpatialReferenceSystem srs) throws IOException {
			marshal(srs.getWellKnownText());
		}
		
		@Override
		protected void body(String wkt) throws IOException {
			cdsect(wkt);
		}
	}
}
