package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Taxon implements Value {

	private String code;
	private String scientificName;
	private String vernacularName;
	private String languageCode;
	private String languageVariant;

	public Taxon(String code) {
		super();
		this.code = code;
	}

	public Taxon(String code, String scientificName, String vernacularName, String languageCode, String languageVariant) {
		super();
		this.code = code;
		this.scientificName = scientificName;
		this.vernacularName = vernacularName;
		this.languageCode = languageCode;
		this.languageVariant = languageVariant;
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

}
