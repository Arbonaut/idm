/**
 * 
 */
package org.openforis.idm.metamodel.impl.jxpath;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.ExpressionContext;
import org.apache.commons.jxpath.FunctionLibrary;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.Pointer;
import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author M. Togna
 * 
 */
public class MetaModelExpression {

	private static JXPathContext CONTEXT;

	static {
		JXPathIntrospector.registerDynamicClass(SchemaObjectDefinition.class, ModelObjectDefinitionDynamicPropertyHandler.class);

		CONTEXT = JXPathContext.newContext(null);
		FunctionLibrary functionLibrary = new FunctionLibrary();
		functionLibrary.addFunctions(new ClassFunctions(MetaModelDefinitionFunctions.class, "idm"));
		CONTEXT.setFunctions(functionLibrary);
	}

	private String expression;

	public MetaModelExpression(String expression) {
		this.setExpression(expression);
	}

	public Object evaluate(SchemaObjectDefinition schemaObjectDefinition) {
		SchemaObjectDefinition context = schemaObjectDefinition;
//		if (context.getParentDefinition() != null) {
//			context = schemaObjectDefinition.getParentDefinition();
//		}
		JXPathContext jxPathContext = JXPathContext.newContext(CONTEXT, context);
		jxPathContext.getVariables().declareVariable("this", schemaObjectDefinition);

		String expr = this.expression.replaceAll("\\bparent\\(\\)", "__parent");
		Object result = jxPathContext.getValue(expr);
		return result;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public static class MetaModelDefinitionFunctions {

		public static Object parent(ExpressionContext context) {
			Pointer pointer = context.getContextNodePointer();
			Object value = pointer.getValue();
			if ((value != null) && (value instanceof SchemaObjectDefinition)) {
				return ((SchemaObjectDefinition) value).getParentDefinition();
			}
			return null;
		}

		public static Object parent(ExpressionContext context, SchemaObjectDefinition schemaObjectDefinition) {
			return schemaObjectDefinition.getParentDefinition();
		}

	}

}
