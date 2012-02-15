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
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

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
		Object node = super.getValue();
		if (node instanceof TimeAttribute) {
			Time time = ((TimeAttribute) node).getValue();
			return time.getHour() * 100 + time.getMinute();
		} else if (node instanceof DateAttribute) {
			Date date = ((DateAttribute) node).getValue();
			return (date.getYear() * 10000) + (date.getMonth() * 100) + date.getDay();
		} else if (node instanceof CodeAttribute) {
			return ((CodeAttribute) node).getValue().getCode();
		} else if (node instanceof Attribute) {
			return ((Attribute<?, ?>) node).getValue();
		}
		return null;
	}

}
