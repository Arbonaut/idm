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
@XmlType(name="", propOrder = {"name", "taxonomy", "highestRank", "qualifiers", "relevantExpression", "required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
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
	
}
