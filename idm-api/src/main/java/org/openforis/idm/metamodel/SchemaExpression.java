/**
 * 
 */
package org.openforis.idm.metamodel;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathIntrospector;

/**
 * @author M. Togna
 * 
 */
class SchemaExpression {

	private static JXPathContext CONTEXT;

	static {
		JXPathIntrospector.registerDynamicClass(NodeDefinition.class, NodeDefinitionPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Schema.class, SchemaPropertyHandler.class);

		CONTEXT = JXPathContext.newContext(null);
	}

	private String expression;

	SchemaExpression(String expression) {
		this.setExpression(expression);
	}

	Object evaluate(NodeDefinition context) {
		if (!(Schema.class.isAssignableFrom(context.getClass()) || NodeDefinition.class.isAssignableFrom(context.getClass()))) {
			throw new IllegalArgumentException("Unable to evaluate expression with context class " + context.getClass().getName());
		}
		JXPathContext jxPathContext = JXPathContext.newContext(CONTEXT, context);

		String expr = this.expression.replaceAll("\\bparent\\(\\)", "__parent");
		Object result = jxPathContext.getValue(expr);
		return result;
	}

	String getExpression() {
		return this.expression;
	}

	void setExpression(String expression) {
		this.expression = expression;
	}

}