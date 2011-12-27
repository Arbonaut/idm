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
class MetaModelExpression {

	private static JXPathContext CONTEXT;

	static {
		JXPathIntrospector.registerDynamicClass(SchemaObjectDefinition.class, SchemaObjectDefinitionPropertyHandler.class);
		JXPathIntrospector.registerDynamicClass(Schema.class, SchemaPropertyHandler.class);

		CONTEXT = JXPathContext.newContext(null);
	}

	private String expression;

	MetaModelExpression(String expression) {
		this.setExpression(expression);
	}

	Object evaluate(ModelDefinition context) {
		if (!(Schema.class.isAssignableFrom(context.getClass()) || SchemaObjectDefinition.class.isAssignableFrom(context.getClass()))) {
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
