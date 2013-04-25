/**
 * 
 */
package org.openforis.idm.transform;

import org.openforis.idm.metamodel.NumericAttributeDefinition;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.NumberAttribute;

/**
 * @author S. Ricci
 *
 * @deprecated replaced with idm-transform api
 */
@Deprecated
public class NumberColumnProvider extends CompositeAttributeColumnProvider {
	
	public NumberColumnProvider(String attributeName) {
		super(attributeName);
	}

	@Override
	protected String[] getFieldsHeadings() {
		return new String[] { getAttributeName() + "_value", getAttributeName() + "_unit"};
	}

	@Override
	protected Field<?>[] getFieldsToExtract(Attribute<?, ?> attr) {
		return new Field[] { attr.getField(0), attr.getField(2) };
	}
	
	@Override
	protected String getFieldValue(Field<?> field) {
		if ( field.getName().equals(NumericAttributeDefinition.UNIT_FIELD) ) {
			NumberAttribute<?, ?> attribute = (NumberAttribute<?, ?>) field.getAttribute();
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
