/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CustomCheck;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Value;
import org.openforis.idm.model.impl.ExpressionImpl;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CustomCheckImpl extends AbstractCheck implements CustomCheck {

	@XmlAttribute(name = "expr")
	private String expression;

	@Override
	public String getExpression() {
		return this.expression;
	}

	@Override
	public boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		ExpressionImpl modelExpression = new ExpressionImpl(expression);
		Boolean b = (Boolean) modelExpression.evaluate(attribute);
		return b;
	}

}
