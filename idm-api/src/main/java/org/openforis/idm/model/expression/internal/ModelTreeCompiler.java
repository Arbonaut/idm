/**
 * 
 */
package org.openforis.idm.model.expression.internal;

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

}
