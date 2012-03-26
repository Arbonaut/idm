/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.ri.EvalContext;
import org.apache.commons.jxpath.ri.compiler.LocationPath;
import org.apache.commons.jxpath.ri.compiler.Step;
import org.apache.commons.jxpath.ri.model.beans.NullPropertyPointer;

/**
 * @author M. Togna
 * 
 */
public class ModelLocationPath extends LocationPath {

	public ModelLocationPath(boolean absolute, Step[] steps) {
		super(absolute, steps);
	}

	@Override
	public Object compute(EvalContext context) {
		Object object = super.compute(context);
		if (object instanceof EvalContext) {
			EvalContext evalContext = (EvalContext) object;
			if (!evalContext.hasNext()) {
				throw new MissingValueException();
			}
		}
		return object;
	}

	@Override
	public Object computeValue(EvalContext context) {
		Object value = super.computeValue(context);
		if (value instanceof NullPropertyPointer) {
			throw new MissingValueException();
		}
		return value;
	}

	// @Override
	// public Iterator<?> iterate(EvalContext context) {
	// Iterator<?> iterator = super.iterate(context);
	// return iterator;
	// }
}
