/**
 * 
 */
package org.openforis.idm.transform;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Field;

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
		return new Field[] { attr.getField(0), attr.getField(1) };
	}
}
