/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.ri.JXPathCompiledExpression;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.Operation;

/**
 * @author M. Togna
 * 
 */
public class ModelJXPathCompiledExpression extends JXPathCompiledExpression {

	public ModelJXPathCompiledExpression(String xpath, Expression expression) {
		super(xpath, expression);
	}

	public List<ModelLocationPath> getReferencedPaths() {
		Expression expression = getExpression();
		List<ModelLocationPath> list = new ArrayList<ModelLocationPath>();
		addReferencePath(expression, list);
		return list;
	}

	private void addReferencePath(Expression expression, List<ModelLocationPath> list) {
		if ( expression instanceof ModelLocationPath ) {
			list.add((ModelLocationPath) expression);
		} else if ( expression instanceof Operation ) {
			Expression[] arguments = ((Operation) expression).getArguments();
			if( arguments != null && arguments.length > 0 ) {
				for (Expression arg : arguments) {
					addReferencePath(arg, list);
				}
			}
		}

	}

}
