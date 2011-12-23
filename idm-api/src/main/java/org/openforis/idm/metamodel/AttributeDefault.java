/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"value", "expression", "condition"})
public class AttributeDefault {

	@XmlAttribute(name = "value")
	private String value;

	@XmlAttribute(name = "expr")
	private String expression;

	@XmlAttribute(name = "if")
	private String condition;

	public String getValue() {
		return value;
	}
	
	public String getExpression() {
		return this.expression;
	}

	public String getCondition() {
		return this.condition;
	}
}
