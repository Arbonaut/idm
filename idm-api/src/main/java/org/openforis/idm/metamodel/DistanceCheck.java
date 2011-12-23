/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Value;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class DistanceCheck extends Check {

	@XmlAttribute(name = "to")
	private String destinationPointExpression;

	@XmlAttribute(name = "min")
	private String minDistanceExpression;

	@XmlAttribute(name = "max")
	private String maxDistanceExpression;

	@XmlAttribute(name = "from")
	private String sourcePointExpression;

	public String getDestinationPointExpression() {
		return this.destinationPointExpression;
	}

	public String getMinDistanceExpression() {
		return this.minDistanceExpression;
	}

	public String getMaxDistanceExpression() {
		return this.maxDistanceExpression;
	}

	public String getSourcePointExpression() {
		return this.sourcePointExpression;
	}

	public boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		// TODO Auto-generated method stub
		return false;
	}

}
