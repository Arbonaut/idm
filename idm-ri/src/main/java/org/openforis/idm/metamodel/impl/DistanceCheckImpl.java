/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.DistanceCheck;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class DistanceCheckImpl extends AbstractCheck implements DistanceCheck {

	@XmlAttribute(name = "to")
	private String destinationPointExpression;

	@XmlAttribute(name = "min")
	private String minDistanceExpression;

	@XmlAttribute(name = "max")
	private String maxDistanceExpression;

	@XmlAttribute(name = "from")
	private String sourcePointExpression;

	@Override
	public String getDestinationPointExpression() {
		return this.destinationPointExpression;
	}

	@Override
	public String getMinDistanceExpression() {
		return this.minDistanceExpression;
	}

	@Override
	public String getMaxDistanceExpression() {
		return this.maxDistanceExpression;
	}

	@Override
	public String getSourcePointExpression() {
		return this.sourcePointExpression;
	}

}
