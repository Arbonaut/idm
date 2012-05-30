/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.Locale;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPointer;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.NumberAttribute;
import org.openforis.idm.model.NumericRange;
import org.openforis.idm.model.NumericRangeAttribute;
import org.openforis.idm.model.RealRange;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author M. Togna
 * @author S. Ricci
 * 
 */
public class ModelNodePointer extends DynamicPointer {

	private static final long serialVersionUID = 1L;
	
	private boolean normalizeNumbers;
	
	protected ModelNodePointer(NodePointer parent, QName name, Object bean, DynamicPropertyHandler handler) {
		super(parent, name, bean, handler);
		normalizeNumbers = false;
	}

	protected ModelNodePointer(QName name, Object bean, DynamicPropertyHandler handler, Locale locale) {
		super(name, bean, handler, locale);
		normalizeNumbers = false;
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
		if (attribute.getValue() == null || !attribute.isFilled() ) {
			return null;
		} else if (attribute instanceof TimeAttribute) {
			Time time = ((TimeAttribute) attribute).getValue();
			return time.getHour() * 100 + time.getMinute();
		} else if (attribute instanceof DateAttribute) {
			Date date = ((DateAttribute) attribute).getValue();
			return (date.getYear() * 10000) + (date.getMonth() * 100) + date.getDay();
		} else if (attribute instanceof CodeAttribute) {
			Code code = ((CodeAttribute) attribute).getValue();
			return code.getCode();
		} else if ( attribute instanceof NumberAttribute<?> ) {
			return getNumericValue((NumberAttribute<?>) attribute);
		} else if ( attribute instanceof NumericRangeAttribute<?, ?>) {
			return getNumericRangeValue((NumericRangeAttribute<?, ?>) attribute);
		} else {
			return attribute.getValue();
		}
	}
	
	private Object getNumericValue(NumberAttribute<?> attr) {
		if ( normalizeNumbers ) {
			return getNormalizedValue(attr);
		} else {
			return attr.getValue();
		}
	}
	
	private Object getNumericRangeValue(NumericRangeAttribute<?, ?> attr) {
		if ( normalizeNumbers ) {
			return getNormalizedValue(attr);
		} else {
			return attr.getValue();
		}
	}
	
	private Number getNormalizedValue(NumberAttribute<? extends Number> attr) {
		Number value = attr.getValue();
		Unit unit = attr.getUnit();
		Number normalizedValue = getNormalizedValue(value, unit);
		return normalizedValue;
	}
	
	private NumericRange<?> getNormalizedValue(NumericRangeAttribute<?, ?> attr) {
		NumericRange<?> range = attr.getValue();
		Unit unit = attr.getUnit();
		if ( unit != null ) {
			Number from = range.getFrom();
			Number to = range.getTo();
			Double normalizedFrom = (Double) getNormalizedValue(from, unit);
			Double normalizedTo = (Double) getNormalizedValue(to, unit);
			RealRange normalizedRange = new RealRange(normalizedFrom, normalizedTo);
			return normalizedRange;
		} else {
			return range;
		}
	}
	
	private Number getNormalizedValue(Number value, Unit unit) {
		if ( value != null && unit != null && unit.getConversionFactor() != null ) {
			Double result = value.doubleValue() * unit.getConversionFactor().doubleValue();
			return result;
		} else {
			return value;
		}
	}
	
	void setNormalizeNumbers(boolean normalizeNumbers) {
		this.normalizeNumbers = normalizeNumbers;
	}
	
	boolean isNormalizeNumbers() {
		return normalizeNumbers;
	}
}
