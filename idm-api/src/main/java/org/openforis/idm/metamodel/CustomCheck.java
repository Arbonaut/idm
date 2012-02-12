/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.geotools.IdmInterpretationError;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidPathException;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CustomCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	@Override
	public boolean validate(Attribute<?, ?> node) {
		String expr = getExpression();
		try {
			RecordContext recordContext = node.getRecord().getContext();
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			CheckExpression checkExpression = expressionFactory.createCheckExpression(expr);
			boolean result = checkExpression.evaluate(node.getParent());
			return result;
		} catch (InvalidPathException e) {
			throw new IdmInterpretationError("Error evaluating custom check", e);
		}
	}
	
}
