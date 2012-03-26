/**
 * 
 */
package org.openforis.idm.model.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathInvalidSyntaxException;
import org.apache.commons.jxpath.Variables;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.internal.ModelJXPathCompiledExpression;
import org.openforis.idm.model.expression.internal.ModelJXPathContext;
import org.openforis.idm.model.expression.internal.ModelLocationPath;
import org.openforis.idm.model.expression.internal.ModelNodePointer;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author M. Togna
 * @author G. Miceli
 */
abstract class AbstractExpression {

	private ModelJXPathContext jxPathContext;
	private ModelJXPathCompiledExpression compiledExpression;

	AbstractExpression(ModelJXPathCompiledExpression compiledExpression, ModelJXPathContext jxPathContext) {
		this.jxPathContext = jxPathContext;
		this.compiledExpression = compiledExpression;
	}

	/**
	 * Returns the list of reference paths of this expression
	 * @return
	 */
	public List<String> getReferencedPaths() {
		List<String> list = new ArrayList<String>();
		List<ModelLocationPath> paths = compiledExpression.getReferencedPaths();
		for (ModelLocationPath path : paths) {
			list.add(path.toString());
		}
		return CollectionUtil.unmodifiableList(list);
	}

	protected Object evaluateSingle(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		try {
			verifyPaths(contextNode);

			JXPathContext jxPathContext = createJXPathContext(contextNode, thisNode);
			Object object = compiledExpression.getValue(jxPathContext);
			return object;
		} catch (IllegalArgumentException e) {
			throw new InvalidExpressionException("Invalid path " + this.compiledExpression.toString());
		} catch (JXPathInvalidSyntaxException e) {
			throw new InvalidExpressionException(e.getMessage());
		}
	}

	/**
	 * Returns a list of Node that matches the expression 
	 * 
	 * @param contextNode
	 * @param thisNode
	 * @return
	 * @throws InvalidExpressionException
	 */
	protected List<Node<?>> evaluateMultiple(Node<?> contextNode, Node<?> thisNode) throws InvalidExpressionException {
		try {
			verifyPaths(contextNode);

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
		} catch (IllegalArgumentException e) {
			throw new InvalidExpressionException("Invalid path " + this.compiledExpression.toString());
		} catch (JXPathInvalidSyntaxException e) {
			throw new InvalidExpressionException(e.getMessage());
		}
	}

	/**
	 * Verifies that the reference paths of this expression matches the definition of the contextNode 
	 * @param contextNode
	 * @throws InvalidExpressionException if the path is invalid
	 */
	private void verifyPaths(Node<?> contextNode) throws InvalidExpressionException {
		List<String> paths = getReferencedPaths();
		for (String path : paths) {
			verifyPath(contextNode, path);
		}
	}

	private void verifyPath(Node<?> contextNode, String path) throws InvalidExpressionException {
		StringTokenizer tokenizer = new StringTokenizer(path, "/");
		NodeDefinition definition = contextNode.getDefinition();
		while (tokenizer.hasMoreTokens()) {
			String name = tokenizer.nextToken();
			definition = verify(definition, name);
		}
	}

	private NodeDefinition verify(NodeDefinition definition, String name) throws InvalidExpressionException {
		if ("__parent".equals(name)) {
			return definition.getParentDefinition();
		}else if("$this".equals(name)){
			return definition;
		} else {
			name = name.replaceAll("\\[\\d+\\]", "");
			if (definition instanceof EntityDefinition) {
				NodeDefinition childDefinition = ((EntityDefinition) definition).getChildDefinition(name);
				if (childDefinition == null) {
					throw new InvalidExpressionException("Invalid path " + compiledExpression.toString());
				}
				return childDefinition;
			}
			throw new InvalidExpressionException("Invalid path " + compiledExpression.toString());
		}
	}

	/**
	 * Creates a new JXPath context in order to evaluate the expression
	 * 
	 * @param contextNode
	 * @param thisNode
	 * @return
	 */
	private JXPathContext createJXPathContext(Node<?> contextNode, Node<?> thisNode) {
		JXPathContext jxPathContext = ModelJXPathContext.newContext(this.jxPathContext, contextNode);
		if (thisNode != null) {
			Variables variables = jxPathContext.getVariables();
			variables.declareVariable("this", thisNode);
		}
		return jxPathContext;
	}

}
