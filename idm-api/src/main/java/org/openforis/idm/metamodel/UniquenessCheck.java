/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Iterator;

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
public class UniquenessCheck extends Check {

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	public boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		ModelExpression modelExpression = new ModelExpression(expression);
		Iterator<?> iterator = modelExpression.Iterate(attribute);
		if (iterator.hasNext()) {
			boolean unique = true;
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof Attribute) {
					@SuppressWarnings("unchecked")
					Value value = ((Attribute<? extends AttributeDefinition, ? extends Value>) object).getValue();
					if (value.equals(attribute.getValue())) {
						unique = false;
						break;
					}
				}
			}
			return unique;
		} else {
			return true;
		}
	}

}
