package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurrence> {

	private static final long serialVersionUID = 1L;

	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getCodeField() {
		return (Field<String>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getScientificNameField() {
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

	public String getScientificName() {
		return getScientificNameField().getValue();
	}
	
	public void setScientificName(String name) {
		getScientificNameField().setValue(name);
		onUpdateValue();
	}

	public String getVernacularName() {
		return getCodeField().getValue();
	}
	
	public void setVernacularName(String name) {
		getVernacularNameField().setValue(name);
		onUpdateValue();
	}

	public String getLanguageCode() {
		return getLanguageCodeField().getValue();
	}
	
	public void setLanguageCode(String lang) {
		getLanguageCodeField().setValue(lang);
		onUpdateValue();
	}

	public String getLanguageVariety() {
		return getLanguageVarietyField().getValue();
	}
	
	public void setLanguageVariety(String var) {
		getLanguageVarietyField().setValue(var);
		onUpdateValue();
	}
	
	@Override
	public TaxonOccurrence getValue() {
		String code = getCodeField().getValue();
		String scientificName = getScientificNameField().getValue();
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
			getScientificNameField().setValue(scientificName);
			getVernacularNameField().setValue(vernacularName);
			getLanguageCodeField().setValue(languageCode);
			getLanguageVarietyField().setValue(languageVariety);
		}
		onUpdateValue();
	}
}
