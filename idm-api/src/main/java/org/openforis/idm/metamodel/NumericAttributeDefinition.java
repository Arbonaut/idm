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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.EnumAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NumericAttributeDefinition extends AttributeDefinition  {

	private static final long serialVersionUID = 1L;

	public enum Type {
		INTEGER, REAL
	}
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(EnumAdapter.class)
	private Type type;

	@XmlElement(name = "precision", type = Precision.class)
	private List<Precision> precisionDefinitions;
	
	public Type getType() {
		return this.type;
	}

	public boolean isInteger() {
		return type == Type.INTEGER;
	}

	public boolean isReal() {
		return type == Type.REAL;
	}
	
	public List<Precision> getPrecisionDefinitions() {
		return Collections.unmodifiableList(this.precisionDefinitions);
	}
}
