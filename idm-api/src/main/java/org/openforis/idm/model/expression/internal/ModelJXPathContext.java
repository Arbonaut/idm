/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.Compiler;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;

/**
 * @author M. Togna
 * 
 */
public class ModelJXPathContext extends JXPathContextReferenceImpl {

	protected ModelJXPathContext(JXPathContext parentContext, Object contextBean) {
		super(parentContext, contextBean);
	}

	public static JXPathContext newContext(Object contextBean) {
		return newContext(null, contextBean);

	}

	public static JXPathContext newContext(JXPathContext parentContext, Object contextBean) {
		return new ModelJXPathContext(parentContext, contextBean);
	}

	@Override
	protected Compiler getCompiler() {
		return new ModelTreeCompiler();
	}

}
