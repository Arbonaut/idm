package org.openforis.idm.model.species;

import java.util.Collections;
import java.util.List;

/**
*	@author G. Miceli
*	@author M. Togna
*   @author E. Wibowo
*/
public class TaxonVernacularName {
	
	private Integer id;
	private String vernacularName;
	private String languageCode;
	private String languageVariety;
	private Integer taxonSystemId;
	private int step;
	private String qualifier1;
	private String qualifier2;
	private String qualifier3;

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

	public String getQualifier1() {
		return qualifier1;
	}

	public void setQualifier1(String qualifier1) {
		this.qualifier1 = qualifier1;
	}

	public String getQualifier2() {
		return qualifier2;
	}

	public void setQualifier2(String qualifier2) {
		this.qualifier2 = qualifier2;
	}

	public String getQualifier3() {
		return qualifier3;
	}

	public void setQualifier3(String qualifier3) {
		this.qualifier3 = qualifier3;
	}



}
