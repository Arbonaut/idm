/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.Locale;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPointer;
import org.openforis.idm.model.Attribute;

/**
 * @author M. Togna
 * 
 */
public class ModelNodePointer extends DynamicPointer {

	private static final long serialVersionUID = 1L;

	protected ModelNodePointer(NodePointer parent, QName name, Object bean, DynamicPropertyHandler handler) {
		super(parent, name, bean, handler);
	}

	protected ModelNodePointer(QName name, Object bean, DynamicPropertyHandler handler, Locale locale) {
		super(name, bean, handler, locale);
	}

	@Override
	public Object getValue() {
		Object value = super.getValue();
		if (value instanceof Attribute<?, ?>) {
			value = ((Attribute<?, ?>) value).getValue();
		}
		return value;
	}

}
