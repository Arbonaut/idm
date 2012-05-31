/**
 * 
 */
package org.openforis.idm.model.expression.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPointer;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Precision;
import org.openforis.idm.metamodel.RangeAttributeDefinition;
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
import org.openforis.idm.util.CollectionUtil;

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
		NumberAttributeDefinition defn = attr.getDefinition();
		List<Unit> units = getUnits(defn);
		if ( units != null && units.size() > 1 ) {
			Unit unit = attr.getUnit();
			Unit defaultUnit = getDefaultUnit(defn);
			if ( unit != null && defaultUnit != null ) {
				Number normalizedValue = getNormalizedValue(value, unit, defaultUnit);
				return normalizedValue;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}
	
	private NumericRange<?> getNormalizedValue(NumericRangeAttribute<?, ?> attr) {
		NumericRange<?> value = attr.getValue();
		RangeAttributeDefinition defn = attr.getDefinition();
		List<Unit> units = getUnits(defn);
		if ( units != null && units.size() > 1 ) {
			Unit unit = attr.getUnit();
			Unit defaultUnit = getDefaultUnit(defn);
			if ( unit != null && defaultUnit != null && unit != defaultUnit ) {
				Number from = value.getFrom();
				Number to = value.getTo();
				Double normalizedFrom = (Double) getNormalizedValue(from, unit, defaultUnit);
				Double normalizedTo = (Double) getNormalizedValue(to, unit, defaultUnit);
				RealRange normalizedValue = new RealRange(normalizedFrom, normalizedTo);
				return normalizedValue;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}
	
	private Number getNormalizedValue(Number value, Unit unit, Unit defaultUnit) {
		if ( value != null && unit.getConversionFactor() != null && defaultUnit.getConversionFactor() != null ) {
			float unitConvFact = unit.getConversionFactor().floatValue();
			float defaultUnitConvFact = defaultUnit.getConversionFactor().floatValue();
			float floatValue = value.floatValue();
			float normalized = floatValue * unitConvFact;
			Float normalizedToDefault = normalized / defaultUnitConvFact;
			return normalizedToDefault;
		} else {
			return value;
		}
	}

	/*
	 * todo move these methods in RangeAttributeDefinition and NumberAttributeDefinition
	 */
	private List<Unit> getUnits(RangeAttributeDefinition defn) {
		List<Precision> precisions = defn.getPrecisionDefinitions();
		return getUnits(precisions);
	}
	
	private List<Unit> getUnits(NumberAttributeDefinition defn) {
		List<Precision> precisions = defn.getPrecisionDefinitions();
		return getUnits(precisions);
	}

	private List<Unit> getUnits(List<Precision> precisions) {
		List<Unit> units = new ArrayList<Unit>();
		for (Precision precision : precisions) {
			Unit unit = precision.getUnit();
			if ( unit != null ) {
				units.add(unit);
			}
		}
		return CollectionUtil.unmodifiableList(units);
	}
	
	private Unit getDefaultUnit(NumberAttributeDefinition defn) {
		List<Precision> precisions = defn.getPrecisionDefinitions();
		return getDefaultUnit(precisions);
	}
	
	private Unit getDefaultUnit(RangeAttributeDefinition defn) {
		List<Precision> precisions = defn.getPrecisionDefinitions();
		return getDefaultUnit(precisions);
	}

	private Unit getDefaultUnit(List<Precision> precisions) {
		for (Precision precision : precisions) {
			Unit unit = precision.getUnit();
			if ( unit != null && precision.isDefaultPrecision() ) {
				return unit;
			}
		}
		return null;
	}
	
	void setNormalizeNumbers(boolean normalizeNumbers) {
		this.normalizeNumbers = normalizeNumbers;
	}
	
	boolean isNormalizeNumbers() {
		return normalizeNumbers;
	}
}
