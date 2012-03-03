package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurrence> {

	private static final long serialVersionUID = 1L;

	TaxonAttribute() {
	}
	
	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition, String.class, String.class, String.class, String.class, String.class);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getCodeField() {
		return (AttributeField<String>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getScientificName() {
		return (AttributeField<String>) getField(1);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getVernacularNameField() {
		return (AttributeField<String>) getField(2);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getLanguageCodeField() {
		return (AttributeField<String>) getField(3);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getLanguageVarietyField() {
		return (AttributeField<String>) getField(4);
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
		if ( value == null ) {
			clearValue();
		} else {
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
}
