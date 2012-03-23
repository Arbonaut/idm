/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.apache.commons.jxpath.ri.compiler.TreeCompiler;

/**
 * @author M. Togna
 * 
 */
public class ModelTreeCompiler extends TreeCompiler {

	@Override
	public Object locationPath(boolean absolute, Object[] steps) {
		return new ModelLocationPath(absolute, toStepArray(steps));
	}

	public Object function(Object name, Object[] args) {
        return new ModelExtensionFunction((QName) name, toExpressionArray(args));
    }
	
	private Step[] toStepArray(Object[] array) {
		Step[] stepArray = null;
		if (array != null) {
			stepArray = new Step[array.length];
			for (int i = 0; i < stepArray.length; i++) {
				stepArray[i] = (Step) array[i];
			}
		}
		return stepArray;
	}
	
	/**
     * Get an Object[] as an Expression[].
     * @param array Object[]
     * @return Expression[]
     */
    private Expression[] toExpressionArray(Object[] array) {
        Expression[] expArray = null;
        if (array != null) {
            expArray = new Expression[array.length];
            for (int i = 0; i < expArray.length; i++) {
                expArray[i] = (Expression) array[i];
            }
        }
        return expArray;
    }

}
