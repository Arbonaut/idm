package org.openforis.idm.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurrence> {

	private static final long serialVersionUID = 1L;
	
	protected static List<String> LANGUAGE_CODES;

	static {
		initLanguageCodesList();
	}

	private static void initLanguageCodesList() {
		List<String> temp = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			String fileName = "lang_codes_iso_639.txt";
			is = TaxonAttribute.class.getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(is));
			String langCode;
			while ((langCode = br.readLine()) != null) {
				temp.add(langCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {}
			}
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
		LANGUAGE_CODES = Collections.unmodifiableList(temp);
	}

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
		if ( lang != null && ! LANGUAGE_CODES.contains(lang) ) {
			throw new LanguageCodeNotSupportedException("Language code not supported: " + lang);
		}
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
		String scientificName = getScientificName();
		String vernacularName = getVernacularName();
		String languageCode = getLanguageCode();
		String languageVariety = getLanguageVariety();
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
			setScientificName(scientificName);
			setVernacularName(vernacularName);
			setLanguageCode(languageCode);
			setLanguageVariety(languageVariety);
		}
		onUpdateValue();
	}
	
	public static class LanguageCodeNotSupportedException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public LanguageCodeNotSupportedException() {
			super();
		}

		public LanguageCodeNotSupportedException(String message, Throwable cause) {
			super(message, cause);
		}

		public LanguageCodeNotSupportedException(String message) {
			super(message);
		}

		public LanguageCodeNotSupportedException(Throwable cause) {
			super(cause);
		}
		
	}
}
