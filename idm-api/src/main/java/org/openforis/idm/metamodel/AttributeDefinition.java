/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.util.CollectionUtil;


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

	@XmlTransient
	private Set<String> checkDependencyPaths;
	
	public List<Check> getChecks() {
		return CollectionUtil.unmodifiableList(this.checks);
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return CollectionUtil.unmodifiableList(this.attributeDefaults);
	}

	public abstract <V> V createValue(String string);
	
}
