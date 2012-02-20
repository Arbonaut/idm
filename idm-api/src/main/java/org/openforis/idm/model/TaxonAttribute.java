package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurrence> {

	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition, 5);
	}

	@Override
	public TaxonOccurrence createValue(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		// currently not required.
		// in the future check if all values are set
		return true;
	}

	@SuppressWarnings("unchecked")
	public Field<String> getCodeField() {
		return (Field<String>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getScientificName() {
		return (Field<String>) getField(1);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getVernacularNameField() {
		return (Field<String>) getField(2);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getLanguageCodeField() {
		return (Field<String>) getField(3);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getLanguageVarietyField() {
		return (Field<String>) getField(4);
	}

	@Override
	public TaxonOccurrence getValue() {
		String code = getCodeField().getValue();
		String scientificName = getScientificName().getValue();
		String vernacularName = getVernacularNameField().getValue();
		String languageCode = getLanguageCodeField().getValue();
		String languageVariety = getLanguageVarietyField().getValue();
		return new TaxonOccurrence(code, scientificName, vernacularName, languageCode, languageVariety);
	}

	@Override
	public void setValue(TaxonOccurrence value) {
		String code = value.getCode();
		String scientificName = value.getScientificName();
		String vernacularName = value.getVernacularName();
		String languageCode = value.getLanguageCode();
		String languageVariety = value.getLanguageVariety();

		getCodeField().setValue(code);
		getScientificName().setValue(scientificName);
		getVernacularNameField().setValue(vernacularName);
		getLanguageCodeField().setValue(languageCode);
		getLanguageVarietyField().setValue(languageVariety);
	}
}
