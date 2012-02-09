/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.validation.CheckResult;

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
	public CheckResult evaluate(Attribute<?, ?> node) {
		String expr = getExpression();
		try {
			RecordContext recordContext = node.getRecord().getContext();
			CheckExpression checkExpression = recordContext.getExpressionFactory().createCheckExpression(expr);
			boolean b = checkExpression.evaluate(node.getParent());
			return new CheckResult(node, this, b);
		} catch (InvalidPathException e) {
			throw new RuntimeException("Unable evaluate expression " + expr, e);
		}
	}
	
}
