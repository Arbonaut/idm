/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;
import org.openforis.idm.model.expression.internal.ModelNodePointer;

/**
 * @author M. Togna
 * 
 */
abstract class AbstractExpression {

	private String expression;
	private JXPathContext jxPathContext;

	protected AbstractExpression(String expression, JXPathContext context) {
		this.expression = expression;
		this.jxPathContext = context;
	}

	protected Object evaluateSingle(Node<? extends NodeDefinition> context) throws InvalidPathException {
		JXPathContext jxPathContext = getContext(context);
		String expr = getExpression();
		Object object = null;
		try {
			object = jxPathContext.getValue(expr);
		} catch (IllegalArgumentException e) {
			throw new InvalidPathException("Invalid path " + this.expression);
		}
		return object;
	}

	protected List<Node<NodeDefinition>> evaluateMultiple(Node<? extends NodeDefinition> context) throws InvalidPathException {
		List<Node<NodeDefinition>> list = new ArrayList<Node<NodeDefinition>>();
		JXPathContext jxPathContext = getContext(context);
		String expr = getExpression();
		Iterator<?> pointers = jxPathContext.iteratePointers(expr);
		while (pointers.hasNext()) {
			ModelNodePointer pointer = (ModelNodePointer) pointers.next();
			Object node = pointer.getNode();
			if (node != null && node instanceof Node) {
				@SuppressWarnings("unchecked")
				Node<NodeDefinition> n = (Node<NodeDefinition>) node;
				list.add(n);
			}
		}
		return list;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Iterator<?> evaluateInternal(Node<? extends NodeDefinition> context) throws InvalidPathException {
		JXPathContext jxPathContext = getContext(context);
		String expr = getExpression();
		try {
			Iterator<?> iterator = jxPathContext.iterate(expr);
			return iterator;
		} catch (IllegalArgumentException e) {
			throw new InvalidPathException("Invalid path " + this.expression);
		}
	}

	protected String getExpression() {
		return expression.replaceAll("\\bparent\\(\\)", "__parent");
	}

	protected JXPathContext getContext(Node<? extends NodeDefinition> context) {
		JXPathContext jxPathContext = ModelJXPathContext.newContext(this.jxPathContext, context);
		if (context instanceof Attribute) {
			@SuppressWarnings("unchecked")
			Object value = ((Attribute<? extends AttributeDefinition, ?>) context).getValue();
			jxPathContext.getVariables().declareVariable("this", value);
		}
		return jxPathContext;
	}

}
