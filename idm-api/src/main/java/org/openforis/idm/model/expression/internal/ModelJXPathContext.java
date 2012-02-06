/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.Compiler;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.openforis.idm.validation.ExternalLookupProvider;

/**
 * @author M. Togna
 * 
 */
public class ModelJXPathContext extends JXPathContextReferenceImpl {

	private ExternalLookupProvider externalLookupProvider;

	protected ModelJXPathContext(JXPathContext parentContext, Object contextBean) {
		super(parentContext, contextBean);
	}

	public static ModelJXPathContext newContext(Object contextBean) {
		return newContext(null, contextBean);
	}

	public static ModelJXPathContext newContext(ModelJXPathContext parentContext, Object contextBean) {
		ModelJXPathContext jxPathContext = new ModelJXPathContext(parentContext, contextBean);
		copyProperties(parentContext, jxPathContext);
		return jxPathContext;
	}

	private static void copyProperties(ModelJXPathContext fromContext, ModelJXPathContext toContext) {
		if ( !(fromContext == null || toContext == null) ) {
			toContext.setExternalLookupProvider(fromContext.getExternalLookupProvider());
		}
	}

	@Override
	protected Compiler getCompiler() {
		return new ModelTreeCompiler();
	}

	public ExternalLookupProvider getExternalLookupProvider() {
		return externalLookupProvider;
	}

	public void setExternalLookupProvider(ExternalLookupProvider lookupProvider) {
		this.externalLookupProvider = lookupProvider;
	}

}
