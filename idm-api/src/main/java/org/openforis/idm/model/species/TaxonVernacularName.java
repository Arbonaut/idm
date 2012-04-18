package org.openforis.idm.model.species;

/**
*	@author G. Miceli
*	@author M. Togna
*/
public class TaxonVernacularName {
	
	private Integer id;
	private String vernacularName;
	private String languageCode;
	private String languageVariety;
	private Integer taxonSystemId;
	private int step;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVernacularName() {
		return vernacularName;
	}

	public void setVernacularName(String name) {
		this.vernacularName = name;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageVariety() {
		return languageVariety;
	}

	public void setLanguageVariety(String languageVariety) {
		this.languageVariety = languageVariety;
	}

	public Integer getTaxonSystemId() {
		return taxonSystemId;
	}

	public void setTaxonSystemId(Integer taxonId) {
		this.taxonSystemId = taxonId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

}
