/**
 * 
 */
package org.openforis.idm.model.expression;

import org.apache.commons.jxpath.JXPathContext;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

/**
 * @author M. Togna
 * 
 */
public class DefaultValueExpression extends AbstractExpression {

	protected DefaultValueExpression(String expression, JXPathContext context) {
		super(expression, context);
	}

	public Object evaluate(Node<? extends NodeDefinition> context) throws InvalidPathException {
		Object object = super.evaluateSingle(context);
		return object;
	}

}
