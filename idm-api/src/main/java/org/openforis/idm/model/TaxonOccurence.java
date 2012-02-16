package org.openforis.idm.model;

import org.openforis.idm.model.species.Taxon;
import org.openforis.idm.model.species.TaxonVernacularName;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class TaxonOccurence {

	private Taxon taxon;
	private TaxonVernacularName vernacularName;

	public TaxonOccurence(Taxon taxon) {
		super();
		this.taxon = taxon;
	}

	public TaxonOccurence(Taxon taxon, TaxonVernacularName vernacularName) {
		super();
		this.taxon = taxon;
		this.vernacularName = vernacularName;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public TaxonVernacularName getVernacularName() {
		return vernacularName;
	}

	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

	public void setVernacularName(TaxonVernacularName vernacularName) {
		this.vernacularName = vernacularName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{code:").append(taxon.getCode());
		sb.append(", scientificName:").append(taxon.getScientificName());
		// TODO append other names
		sb.append("}");
		return sb.toString();
	}
}
