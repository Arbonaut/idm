/**
 * 
 */
package org.openforis.idm.model.expression;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * 
 */
public class DefaultValueExpression extends AbstractExpression {

	public DefaultValueExpression(String expression) {
		super(expression);
	}

	public Object evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		Object object = super.evaluateSingle(context);
		return object;
	}

}
