/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.apache.commons.jxpath.ri.compiler.TreeCompiler;
import org.openforis.idm.model.expression.internal.ModelRelationalExpression.Operation;

/**
 * @author M. Togna
 * 
 */
public class ModelTreeCompiler extends TreeCompiler {

	@Override
	public Object locationPath(boolean absolute, Object[] steps) {
		return new ModelLocationPath(absolute, toStepArray(steps));
	}

	@Override
	public Object function(Object name, Object[] args) {
		return new ModelExtensionFunction((QName) name, toExpressionArray(args));
	}

	@Override
	public Object lessThan(Object left, Object right) {
		return new ModelRelationalExpression(Operation.LT, (Expression) left, (Expression) right);
	}

	@Override
	public Object lessThanOrEqual(Object left, Object right) {
		return new ModelRelationalExpression(Operation.LTE, (Expression) left, (Expression) right);
	}

	@Override
	public Object greaterThan(Object left, Object right) {
		return new ModelRelationalExpression(Operation.GT, (Expression) left, (Expression) right);
	}

	@Override
	public Object greaterThanOrEqual(Object left, Object right) {
		return new ModelRelationalExpression(Operation.GTE, (Expression) left, (Expression) right);
	}

	@Override
	public Object equal(Object left, Object right) {
		return new ModelRelationalExpression(Operation.EQ, (Expression) left, (Expression) right);
	}

	@Override
	public Object notEqual(Object left, Object right) {
		return new ModelRelationalExpression(Operation.NOTEQ, (Expression) left, (Expression) right);
	}

	private Step[] toStepArray(Object[] array) {
		Step[] stepArray = null;
		if ( array != null ) {
			stepArray = new Step[array.length];
			for ( int i = 0 ; i < stepArray.length ; i++ ) {
				stepArray[i] = (Step) array[i];
			}
		}
		return stepArray;
	}

	/**
	 * Get an Object[] as an Expression[].
	 * 
	 * @param array
	 *            Object[]
	 * @return Expression[]
	 */
	private Expression[] toExpressionArray(Object[] array) {
		Expression[] expArray = null;
		if ( array != null ) {
			expArray = new Expression[array.length];
			for ( int i = 0 ; i < expArray.length ; i++ ) {
				expArray[i] = (Expression) array[i];
			}
		}
		return expArray;
	}

}
