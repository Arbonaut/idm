/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.TextAttributeDefinition;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class TextAttributeDefinitionImpl extends AbstractAttributeDefinition implements TextAttributeDefinition {
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = TypeAdapter.class)
	private Type type;

	@Override
	public Type getType() {
		return this.type;
	}

	private static class TypeAdapter extends XmlAdapter<String, Type> {

		@Override
		public Type unmarshal(String v) throws Exception {
			return Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Type v) throws Exception {
			return v.toString().toLowerCase();
		}

	}
}
