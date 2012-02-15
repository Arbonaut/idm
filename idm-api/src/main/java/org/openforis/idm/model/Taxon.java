package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Taxon {

	private final String code;
	private final String scientificName;
	private final String vernacularName;
	private final String languageCode;
	private final String languageVariant;

	private Integer taxonId;
	private Integer taxonVernacularNameId;
	
	public Taxon(String code, String scientificName, String vernacularName, String languageCode, String languageVariant) {
		super();
		this.code = code;
		this.scientificName = scientificName;
		this.vernacularName = vernacularName;
		this.languageCode = languageCode;
		this.languageVariant = languageVariant;
	}

	public Integer getTaxonId() {
		return taxonId;
	}
	
	public Integer getTaxonVernacularNameId() {
		return taxonVernacularNameId;
	}
	
	public String getCode() {
		return code;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public String getLanguageVariant() {
		return languageVariant;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getVernacularName() {
		return vernacularName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Taxon other = (Taxon) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{code:").append(code);
		sb.append(", scientificName:").append(scientificName);
		// TODO append other names
		sb.append("}");
		return sb.toString();
	}	
}
