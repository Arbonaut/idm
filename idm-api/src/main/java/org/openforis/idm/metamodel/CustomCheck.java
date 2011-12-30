/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.mockito.asm.tree.analysis.Value;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.ModelExpression;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CustomCheck extends Check {

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	public boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		ModelExpression modelExpression = new ModelExpression(expression);
		Boolean b = (Boolean) modelExpression.evaluate(attribute);
		return b;
	}
}
