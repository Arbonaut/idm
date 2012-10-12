package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;

/**
 * 
 * @author G. Miceli
 *
 */
class UnitsIM extends AbstractIdmlMarshaller<Unit, Survey> {

	UnitsIM() {
		super(UNIT);
		setListWrapperTag(UNITS);
		addChildMarshallers(new LabelIM(), new AbbreviationIM());
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		List<Unit> units = survey.getUnits();
		marshal(units);
	}
	
	@Override
	protected void attributes(Unit unit) throws IOException {
		attribute(ID, unit.getId());
		attribute(NAME, unit.getName());
		attribute(DIMENSION, unit.getDimension());
		attribute(CONVERSION_FACTOR, unit.getConversionFactor());
	}
	
	private class LabelIM extends LanguageSpecificTextIM<Unit> {

		public LabelIM() {
			super(LABEL);
		}
		
		@Override
		protected void marshalInstances(Unit unit) throws IOException {
			marshal(unit.getLabels());
		}
	}
	
	private class AbbreviationIM extends LanguageSpecificTextIM<Unit> {

		public AbbreviationIM() {
			super(ABBREVIATION);
		}
		
		@Override
		protected void marshalInstances(Unit unit) throws IOException {
			marshal(unit.getAbbreviations());
		}
	}
}
