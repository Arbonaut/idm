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
import org.openforis.idm.model.BooleanValue;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.IntegerValue;
import org.openforis.idm.model.NumericRange;
import org.openforis.idm.model.RealValue;
import org.openforis.idm.model.TextValue;
import org.openforis.idm.model.Time;

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
		if (node instanceof Attribute) {
			return getValue((Attribute<?, ?>) node);
		} else {
			return node;
		}
	}

	private Object getValue(Attribute<?, ?> attribute) {
		Object value = attribute.getValue();
		if ( value instanceof TextValue ) {
			return ((TextValue) value).getValue(); 
		} else if ( value instanceof IntegerValue ) {		
			return ((IntegerValue) value).getValue(); 
		} else if ( value instanceof RealValue ) {		
			return ((RealValue) value).getValue(); 
		} else if ( value instanceof NumericRange ) {		
			return value; 
		} else if ( value instanceof BooleanValue ) {		
			return ((BooleanValue) value).getValue(); 
		} else if (value instanceof Time ) {
			Time time = (Time) value;
			return time.getHour() * 100 + time.getMinute();
		} else if (value instanceof Date) {
			Date date = (Date) value;
			return (date.getYear() * 10000) + (date.getMonth() * 100) + date.getDay();
		} else if (value instanceof Code) {
			Code code = (Code) value;
			return code.getCode();
		} else {
			throw new UnsupportedOperationException("Unsupported value type of "+attribute.getClass());
		}
	}

}
