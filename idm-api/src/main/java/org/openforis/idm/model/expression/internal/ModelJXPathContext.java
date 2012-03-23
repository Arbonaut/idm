/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.Compiler;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.Parser;
import org.apache.commons.jxpath.ri.compiler.Expression;
import org.openforis.idm.metamodel.validation.LookupProvider;

/**
 * @author M. Togna
 * @author G. Miceli
 */
@SuppressWarnings("rawtypes")
public class ModelJXPathContext extends JXPathContextReferenceImpl {

	private static int cleanupCount = 0;
	private static final Compiler COMPILER = new ModelTreeCompiler();
	// The frequency of the cache cleanup
	private static final int CLEANUP_THRESHOLD = 500;

	private LookupProvider lookupProvider;
	private Map<String, Object> compiled;

	protected ModelJXPathContext(JXPathContext parentContext, Object contextNode) {
		super(parentContext, contextNode);
		this.compiled = new HashMap<String, Object>();
	}

	public static ModelJXPathContext newContext(JXPathContext parentContext, Object contextNode) {
		ModelJXPathContext jxPathContext = new ModelJXPathContext(parentContext, contextNode);
		copyProperties(parentContext, jxPathContext);
		return jxPathContext;
	}

	private static void copyProperties(JXPathContext fromContext, ModelJXPathContext toContext) {
		if (!(fromContext == null || toContext == null)) {
			if (fromContext instanceof ModelJXPathContext) {
				toContext.setLookupProvider(((ModelJXPathContext) fromContext).getLookupProvider());
			}
		}
	}
    
	@Override
	protected CompiledExpression compilePath(String xpath) {
		Expression expr = compileExpression(xpath);
		CompiledExpression compiledExpression = new ModelJXPathCompiledExpression(xpath, expr);
		return compiledExpression;
	}

	@Override
	protected Compiler getCompiler() {
		return COMPILER;
	}

	public LookupProvider getLookupProvider() {
		return lookupProvider;
	}

	public void setLookupProvider(LookupProvider lookupProvider) {
		this.lookupProvider = lookupProvider;
	}

	@SuppressWarnings("unchecked")
	private Expression compileExpression(String xpath) {
		Expression expr;

		synchronized (compiled) {
			if (USE_SOFT_CACHE) {
				expr = null;
				SoftReference ref = (SoftReference) compiled.get(xpath);
				if (ref != null) {
					expr = (Expression) ref.get();
				}
			} else {
				expr = (Expression) compiled.get(xpath);
			}
		}

		if (expr != null) {
			return expr;
		}

		expr = (Expression) Parser.parseExpression(xpath, getCompiler());

		synchronized (compiled) {
			if (USE_SOFT_CACHE) {
				if (cleanupCount++ >= CLEANUP_THRESHOLD) {
					Iterator it = compiled.entrySet().iterator();
					while (it.hasNext()) {
						Entry me = (Entry) it.next();
						if (((SoftReference) me.getValue()).get() == null) {
							it.remove();
						}
					}
					cleanupCount = 0;
				}
				compiled.put(xpath, new SoftReference(expr));
			} else {
				compiled.put(xpath, expr);
			}
		}

		return expr;
	}
}
