/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.Arrays;

import org.apache.commons.jxpath.Function;
import org.apache.commons.jxpath.JXPathFunctionNotFoundException;
import org.apache.commons.jxpath.NodeSet;
import org.apache.commons.jxpath.ri.EvalContext;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.axes.NodeSetContext;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.ExtensionFunction;

/**
 * @author M. Togna
 * 
 */
public class ModelExtensionFunction extends ExtensionFunction {

	public ModelExtensionFunction(QName functionName, Expression[] args) {
		super(functionName, args);
	}

	@Override
	public Object computeValue(EvalContext context) {
		Object[] parameters = null;
		if (args != null) {
			parameters = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				Expression expression = args[i];
				Object computeValue = expression.computeValue(context);
				parameters[i] = convert(computeValue);
			}
		}

		Function function = context.getRootContext().getFunction(getFunctionName(), parameters);
		if (function == null) {
			throw new JXPathFunctionNotFoundException("No such function: " + getFunctionName() + Arrays.asList(parameters));
		}
		Object result = function.invoke(context, parameters);
		return result instanceof NodeSet ? new NodeSetContext(context, (NodeSet) result) : result;

	}

	/**
	 * Convert any incoming context to a value.
	 * 
	 * @param object
	 *            Object to convert
	 * @return context value or <code>object</code> unscathed.
	 */
	private Object convert(Object object) {
		if (object instanceof EvalContext) {
			return ((EvalContext) object).getValue();
		} else {
			return object;
		}
	}

}
