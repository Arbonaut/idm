/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.ri.JXPathCompiledExpression;
import org.apache.commons.jxpath.ri.compiler.CoreOperation;
import org.apache.commons.jxpath.ri.compiler.Expression;

/**
 * @author M. Togna
 * 
 */
public class ModelJXPathCompiledExpression extends JXPathCompiledExpression {

	public ModelJXPathCompiledExpression(String xpath, Expression expression) {
		super(xpath, expression);
	}

	public List<ModelLocationPath> getReferencePaths() {
		Expression expression = getExpression();
		List<ModelLocationPath> list = new ArrayList<ModelLocationPath>();
		addReferencePath(expression, list);
		return list;
	}

	private void addReferencePath(Expression expression, List<ModelLocationPath> list) {
		if (expression instanceof ModelLocationPath) {
			list.add((ModelLocationPath) expression);
		} else if (expression instanceof CoreOperation) {
			Expression[] arguments = ((CoreOperation) expression).getArguments();
			for (Expression arg : arguments) {
				addReferencePath(arg, list);
			}
		}

	}

}
