/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Precision;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class AbstractNumericAttributeDefinition extends AbstractAttributeDefinition implements NumberAttributeDefinition {

	@XmlElement(name = "precision", type = PrecisionImpl.class)
	private List<Precision> precisionDefinitions;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = TypeAdapter.class)
	private Type type;

	@Override
	public List<Precision> getPrecisionDefinitions() {
		return this.precisionDefinitions;
	}

	@Override
	public void setPrecisionDefinitions(List<Precision> precisionDefinitions) {
		this.precisionDefinitions = precisionDefinitions;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
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
