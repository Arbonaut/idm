/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.TaxonAttribute;
import org.openforis.idm.model.TaxonOccurrence;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * @author W. Eko
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "taxonomy", "highestRank", "qualifiers", "relevantExpression", "required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})		
public class TaxonAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "qualifiers")
	private String qualifiers;
	
	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("code", "c", "code", String.class, this), 
			new FieldDefinition<String>("scientific_name", "s", "name", String.class, this), 
			new FieldDefinition<String>("vernacular_name", "v", "vn", String.class, this), 
			new FieldDefinition<String>("language_code", "l", "lang", String.class, this), 
			new FieldDefinition<String>("language_variety", "lv", "lang_var", String.class, this)
	};
	
	@XmlAttribute(name = "taxonomy")
	private String taxonomy;

	@XmlAttribute(name = "highestRank")
	private String highestRank;

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

	public String getHighestRank() {
		return highestRank;
	}
	
	public List<String> getQualifiers() {
		if ( qualifiers != null ) {
			String[] exprs = qualifiers.split(",");
			return Collections.unmodifiableList(Arrays.asList(exprs));
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((highestRank == null) ? 0 : highestRank.hashCode());
		result = prime * result + ((qualifiers == null) ? 0 : qualifiers.hashCode());
		result = prime * result + ((taxonomy == null) ? 0 : taxonomy.hashCode());
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
		if (highestRank == null) {
			if (other.highestRank != null)
				return false;
		} else if (!highestRank.equals(other.highestRank))
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
