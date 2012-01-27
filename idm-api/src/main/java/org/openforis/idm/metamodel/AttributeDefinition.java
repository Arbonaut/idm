/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class AttributeDefinition extends NodeDefinition {

	private static final long serialVersionUID = 1L;

	@XmlElements({ 
		@XmlElement(name = "distance", type = DistanceCheck.class), 
		@XmlElement(name = "pattern",  type = PatternCheck.class),
		@XmlElement(name = "compare",  type = ComparisonCheck.class), 
		@XmlElement(name = "check",    type = CustomCheck.class),
		@XmlElement(name = "unique",   type = UniquenessCheck.class) })
	private List<Check> checks;

	@XmlElement(name = "default", type = AttributeDefault.class)
	private List<AttributeDefault> attributeDefaults;

	public List<Check> getChecks() {
		return Collections.unmodifiableList(this.checks);
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return Collections.unmodifiableList(this.attributeDefaults);
	}
}
