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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.xml.internal.TextAttributeDefinitionTypeAdapter;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.TextAttribute;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "type", "key","required", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
	"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TextAttributeDefinition extends AttributeDefinition implements KeyAttributeDefinition {

	static List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
			new FieldDefinition("value", "v", String.class)
		));
	
	public enum Type {
		SHORT, MEMO
	}

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(TextAttributeDefinitionTypeAdapter.class)
	private Type type;

	@XmlAttribute(name = "key")
	private Boolean key;

	public Type getType() {
		return this.type;
	}
	
	@Override
	public boolean isKey() {
		return this.key == null ? false : key;
	}

	@Override
	public Node<?> createNode() {
		return new TextAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return string;
		}
	}
	
	@Override
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldsDefinitions;
	}
	
}
