/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.ModelPathExpression;

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

	@Override
	public boolean evaluate(Attribute<?, ?> attribute) {
		try {
			RecordContext recordContext = attribute.getRecord().getContext();
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			ModelPathExpression pathExpression = expressionFactory.createModelPathExpression(expression);
			List<Node<?>> list = pathExpression.iterate(attribute.getParent(), attribute);
			boolean unique = true;
			if (list != null && list.size() > 0) {
				for (Node<?> node : list) {
					if (node != attribute) {
						if (node instanceof Attribute) {
							Object value = ((Attribute<?, ?>) node).getValue();
							if (value.equals(attribute.getValue())) {
								unique = false;
								break;
							}
						}
					}
				}
			}
			return unique;
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Error evaluating uniqueness check", e);
		}
	}

}
