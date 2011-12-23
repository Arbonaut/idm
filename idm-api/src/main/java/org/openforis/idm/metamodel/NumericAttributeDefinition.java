/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NumericAttributeDefinition extends AttributeDefinition  {

	public enum Type {
		INTEGER, REAL
	}
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = TypeAdapter.class)
	private Type type;

	@XmlElement(name = "precision", type = Precision.class)
	private List<Precision> precisionDefinitions;
	
	public Type getType() {
		return this.type;
	}

	public List<Precision> getPrecisionDefinitions() {
		return Collections.unmodifiableList(this.precisionDefinitions);
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
