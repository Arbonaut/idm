/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.ExplicitCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class AbstractAttributeDefinition extends AbstractModelObjectDefinition implements AttributeDefinition {

	@XmlElements({ @XmlElement(name = "distance", type = DistanceCheckImpl.class), @XmlElement(name = "pattern", type = PatternCheckImpl.class),
			@XmlElement(name = "compare", type = ComparisonCheckImpl.class), @XmlElement(name = "check", type = CustomCheckImpl.class),
			@XmlElement(name = "unique", type = UniquenessCheckImpl.class) })
	private List<ExplicitCheck> explicitChecks;

	@XmlElement(name = "default", type = AttributeDefaultImpl.class)
	private List<AttributeDefault> attributeDefaults;

	@Override
	public List<ExplicitCheck> getExplicitChecks() {
		return this.explicitChecks;
	}

	@Override
	public void setExplicitCheck(List<ExplicitCheck> explicitCheck) {
		this.explicitChecks = explicitCheck;
	}

	@Override
	public List<AttributeDefault> getAttributeDefaults() {
		return this.attributeDefaults;
	}

	@Override
	public void setAttributeDefaults(List<AttributeDefault> attributeDefaults) {
		this.attributeDefaults = attributeDefaults;
	}

	@Override
	public String toString() {
		return "Attribute " + this.getName();
	}

}