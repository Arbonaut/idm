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
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.TaxonAttribute;
import org.openforis.idm.model.TaxonOccurrence;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author W. Eko
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
/*@XmlType(name="", propOrder = {"name", "relevantExpression","qualifiers", "required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})*/		
public class TaxonAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	//@XmlAttribute(name = "qualifiers")
	//private String qualifierExpressions;
	
	static List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
			new FieldDefinition("code", "c", String.class), 
			new FieldDefinition("scientificName", "s", String.class), 
			new FieldDefinition("vernacularName", "v", String.class), 
			new FieldDefinition("languageCode", "l", String.class), 
			new FieldDefinition("languageVariety", "lv", String.class)
		));
	
	@Override
	public Node<?> createNode() {
		return new TaxonAttribute(this);
	}

	/*public List<String> getQualifierExpressions() {
		String[] exprs = qualifierExpressions.split(",");
		return Arrays.asList(exprs);
	}*/
	@SuppressWarnings("unchecked")
	@Override
	public TaxonOccurrence createValue(String string) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<FieldDefinition> getFieldsDefinitions() {
		return fieldsDefinitions;
	}
	
	
	
}
