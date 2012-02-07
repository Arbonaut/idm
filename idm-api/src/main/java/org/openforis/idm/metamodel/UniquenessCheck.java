/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.model.expression.ModelPathExpression;
import org.openforis.idm.validation.ValidationContext;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class UniquenessCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	public boolean execute(ValidationContext validationContext, Attribute<? extends AttributeDefinition, ?> attribute) throws InvalidPathException {
		ModelPathExpression pathExpression = validationContext.getExpressionFactory().createModelPathExpression(getExpression());
		List<Node<?>> list = pathExpression.iterate(attribute);
		if (list != null && list.size() > 0) {
			boolean unique = true;
			for (Node<?> object : list) {
				if (object instanceof Attribute) {
					Object value = ((Attribute<?, ?>) object).getValue();
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
