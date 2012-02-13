/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Variables;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;
import org.openforis.idm.model.expression.internal.ModelLocationPath;
import org.openforis.idm.model.expression.internal.ModelNodePointer;

/**
 * @author M. Togna
 * @author G. Miceli 
 */
abstract class AbstractExpression {

	private ModelJXPathContext jxPathContext;
	private ModelJXPathCompiledExpression compiledExpression;
	
	protected AbstractExpression(ModelJXPathCompiledExpression compiledExpression, ModelJXPathContext jxPathContext) {
		this.jxPathContext = jxPathContext;
		this.compiledExpression = compiledExpression;
	}

	public List<String> getReferencePaths() {
		List<String> list = new ArrayList<String>();
		List<ModelLocationPath> paths = compiledExpression.getReferencePaths();
		for (ModelLocationPath path : paths) {
			list.add(path.toString());
		}
		return list;
	}

	protected Object evaluateSingle(Node<?> contextNode, Node<?> thisNode) throws InvalidPathException {
		JXPathContext jxPathContext = createJXPathContext(contextNode, thisNode);
		Object object = null;
		try {
			object = compiledExpression.getValue(jxPathContext);
		} catch (IllegalArgumentException e) {
			throw new InvalidPathException("Invalid path " + this.compiledExpression.toString());
		}
		return object;
	}

	// protected Object evaluateSingle(Node<? extends NodeDefinition> context) throws InvalidPathException {
	// JXPathContext jxPathContext = createJXPathContext(context);
	// String expr = getNormalizedExpression();
	// Object object = null;
	// try {
	// object = jxPathContext.getValue(expr);
	// } catch (IllegalArgumentException e) {
	// throw new InvalidPathException("Invalid path " + this.expression);
	// }
	// return object;
	// }

	protected List<Node<?>> evaluateMultiple(Node<?> contextNode, Node<?> thisNode) throws InvalidPathException {
		List<Node<?>> list = new ArrayList<Node<?>>();
		JXPathContext jxPathContext = createJXPathContext(contextNode, thisNode);

		Iterator<?> pointers = compiledExpression.iteratePointers(jxPathContext);
		while (pointers.hasNext()) {
			ModelNodePointer pointer = (ModelNodePointer) pointers.next();
			Object ptrNode = pointer.getNode();
			if (ptrNode != null && ptrNode instanceof Node) {
				Node<?> n = (Node<?>) ptrNode;
				list.add(n);
			}
		}
		return list;
	}

	// protected List<Node<?>> evaluateMultiple(Node<? extends NodeDefinition> context) throws InvalidPathException {
	// List<Node<?>> list = new ArrayList<Node<?>>();
	// JXPathContext jxPathContext = createJXPathContext(context);
	// String expr = getNormalizedExpression();
	// Iterator<?> pointers = jxPathContext.iteratePointers(expr);
	// while (pointers.hasNext()) {
	// ModelNodePointer pointer = (ModelNodePointer) pointers.next();
	// Object node = pointer.getNode();
	// if (node != null && node instanceof Node) {
	// @SuppressWarnings("unchecked")
	// Node<NodeDefinition> n = (Node<NodeDefinition>) node;
	// list.add(n);
	// }
	// }
	// return list;
	// }

	// @SuppressWarnings("unused")
	// @Deprecated
	// private Iterator<?> evaluateInternal(Node<? extends NodeDefinition> context) throws InvalidPathException {
	// JXPathContext jxPathContext = createJXPathContext(context);
	// String expr = getNormalizedExpression();
	// try {
	// Iterator<?> iterator = jxPathContext.iterate(expr);
	// return iterator;
	// } catch (IllegalArgumentException e) {
	// throw new InvalidPathException("Invalid path " + this.expression);
	// }
	// }

	protected JXPathContext createJXPathContext(Node<?> contextNode, Node<?> thisNode) {
		JXPathContext jxPathContext = ModelJXPathContext.newContext(this.jxPathContext, contextNode);
//		if (context instanceof Attribute) {
//			@SuppressWarnings("unchecked")
//			Object value = ((Attribute<? extends AttributeDefinition, ?>) context).getValue();
//			jxPathContext.getVariables().declareVariable("this", value);
//		}
		if ( thisNode != null ) {
			Variables variables = jxPathContext.getVariables();
			variables.declareVariable("this", thisNode);
		}
		return jxPathContext;
	}
}
