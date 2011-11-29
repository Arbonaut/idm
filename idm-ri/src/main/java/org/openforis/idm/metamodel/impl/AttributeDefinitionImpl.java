/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.ValueCheck;

/**
 * @author Mino Togna
 * 
 */
public class AttributeDefinitionImpl<C extends ValueCheck> extends AbstractModelObjectDefinition implements AttributeDefinition<C> {

	private List<C> valueChecks;
	private List<AttributeDefault> attributeDefaults;

	public List<C> getValueChecks() {
		return valueChecks;
	}

	public void setValueChecks(List<C> valueChecks) {
		this.valueChecks = valueChecks;
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return attributeDefaults;
	}

	public void setAttributeDefaults(List<AttributeDefault> attributeDefaults) {
		this.attributeDefaults = attributeDefaults;
	}

}
