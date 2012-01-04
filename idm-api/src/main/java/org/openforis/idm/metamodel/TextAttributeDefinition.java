/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "type", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
	"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TextAttributeDefinition extends AttributeDefinition {

	public enum Type {
		SHORT, MEMO
	}

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = TypeAdapter.class)
	private Type type;

	public Type getType() {
		return this.type;
	}

	private static class TypeAdapter extends XmlAdapter<String, Type> {
		@Override
		public Type unmarshal(String v) throws Exception {
			return v==null ? null : Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Type v) throws Exception {
			return v==null ? null : v.toString().toLowerCase();
		}

	}
}
