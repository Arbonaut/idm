/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.TaxonAttribute;
import org.openforis.idm.model.TaxonOccurrence;
import org.openforis.idm.model.Value;
import org.openforis.idm.model.species.Taxon.TaxonRank;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * @author W. Eko
 */
public class TaxonAttributeDefinition extends AttributeDefinition {

	private static final String QUALIFIER_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	private String qualifiers;
	
	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("code", "c", "code", String.class, this), 
			new FieldDefinition<String>("scientific_name", "s", "name", String.class, this), 
			new FieldDefinition<String>("vernacular_name", "v", "vn", String.class, this), 
			new FieldDefinition<String>("language_code", "l", "lang", String.class, this), 
			new FieldDefinition<String>("language_variety", "lv", "lang_var", String.class, this)
	};
	
	private String taxonomy;
	private TaxonRank highestTaxonRank;
	
	TaxonAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	@Override
	public Node<?> createNode() {
		return new TaxonAttribute(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TaxonOccurrence createValue(String string) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return TaxonOccurrence.class;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	@Deprecated
	public String getHighestRank() {
		return highestTaxonRank == null ? null: highestTaxonRank.getName();
	}
	
	@Deprecated
	public void setHighestRank(String highestRank) {
		TaxonRank rank = TaxonRank.fromName(highestRank);
		setHighestTaxonRank(rank);
	}

	public TaxonRank getHighestTaxonRank() {
		return highestTaxonRank;
	}
	
	public void setHighestTaxonRank(TaxonRank highestTaxonRank) {
		this.highestTaxonRank = highestTaxonRank;
	}
	
	public List<String> getQualifiers() {
		if ( qualifiers != null ) {
			String[] exprs = qualifiers.split(QUALIFIER_SEPARATOR);
			return Collections.unmodifiableList(Arrays.asList(exprs));
		} else {
			return Collections.emptyList();
		}
	}

	public void setQualifiers(String qualifiers) {
		this.qualifiers = qualifiers != null && qualifiers.length() > 0 ? qualifiers: null;
	}

	public void setQualifiers(List<String> qualifiers) {
		setQualifiers(StringUtils.join(qualifiers, QUALIFIER_SEPARATOR));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((highestTaxonRank == null) ? 0 : highestTaxonRank.hashCode());
		result = prime * result
				+ ((qualifiers == null) ? 0 : qualifiers.hashCode());
		result = prime * result
				+ ((taxonomy == null) ? 0 : taxonomy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxonAttributeDefinition other = (TaxonAttributeDefinition) obj;
		if (highestTaxonRank != other.highestTaxonRank)
			return false;
		if (qualifiers == null) {
			if (other.qualifiers != null)
				return false;
		} else if (!qualifiers.equals(other.qualifiers))
			return false;
		if (taxonomy == null) {
			if (other.taxonomy != null)
				return false;
		} else if (!taxonomy.equals(other.taxonomy))
			return false;
		return true;
	}
	
}
