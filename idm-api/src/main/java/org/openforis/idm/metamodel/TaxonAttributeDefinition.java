/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.TaxonAttribute;
import org.openforis.idm.model.TaxonOccurrence;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TaxonAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	static List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
			new FieldDefinition("code", "c", String.class), 
			new FieldDefinition("scientific_name", "s", String.class), 
			new FieldDefinition("vernacular_name", "v", String.class), 
			new FieldDefinition("language_code", "l", String.class), 
			new FieldDefinition("language_variety", "lv", String.class)
		));
	
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
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldsDefinitions;
	}
	
}
