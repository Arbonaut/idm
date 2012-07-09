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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.TextAttributeDefinitionTypeAdapter;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.TextAttribute;
import org.openforis.idm.model.TextValue;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "type", "key","required", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
	"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TextAttributeDefinition extends AttributeDefinition implements KeyAttributeDefinition {

	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<String>("value", "v", null, String.class, this)
	};
	
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
	public TextValue createValue(String string) {
		return new TextValue(string);
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}
	
	@Override
	public Class<? extends Value> getValueType() {
		return TextValue.class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TextAttributeDefinition other = (TextAttributeDefinition) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
