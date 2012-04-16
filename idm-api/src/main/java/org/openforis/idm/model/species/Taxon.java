package org.openforis.idm.model.species;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
public class Taxon {

	private Integer systemId;
	private Integer taxonId;
	private Integer parentId;
	private Integer taxonomyId;
	private String code;
	private String scientificName;
	private String taxonomicRank;
	private int step;

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public Integer getTaxonId() {
		return taxonId;
	}

	public void setTaxonId(Integer taxonId) {
		this.taxonId = taxonId;
	}

	public Integer getTaxonomyId() {
		return taxonomyId;
	}

	public void setTaxonomyId(Integer taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getTaxonomicRank() {
		return taxonomicRank;
	}

	public void setTaxonomicRank(String taxonomicRank) {
		this.taxonomicRank = taxonomicRank;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
