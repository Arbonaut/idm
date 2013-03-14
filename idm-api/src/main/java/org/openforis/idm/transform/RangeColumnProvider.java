/**
 * 
 */
package org.openforis.idm.transform;

import org.openforis.idm.metamodel.NumericAttributeDefinition;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.NumericRangeAttribute;

/**
 * @author S. Ricci
 *
 * @deprecated replaced with idm-transform api
 */
@Deprecated
public class RangeColumnProvider extends CompositeAttributeColumnProvider {
	
	public RangeColumnProvider(String attributeName) {
		super(attributeName);
	}

	@Override
	protected String[] getFieldsHeadings() {
		return new String[] { getAttributeName() + "_from", getAttributeName() + "_to", 
				getAttributeName() + "_unit"};
	}

	@Override
	protected Field<?>[] getFieldsToExtract(Attribute<?, ?> attr) {
		return new Field[] { attr.getField(0), attr.getField(1), attr.getField(3) };
	}
	
	@Override
	protected String getFieldValue(Field<?> field) {
		if ( field.getName().equals(NumericAttributeDefinition.UNIT_FIELD) ) {
			NumericRangeAttribute<?, ?> attribute = (NumericRangeAttribute<?, ?>) field.getAttribute();
			Unit unit = attribute.getUnit();
			if ( unit == null ) {
				return "";
			} else {
				return unit.getName();
			}
		} else {
			return super.getFieldValue(field);
		}
	}
}
