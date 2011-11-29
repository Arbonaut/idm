/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.ValueCheck;

/**
 * @author M. Togna
 * 
 */
public class AttributeDefinitionImpl<C extends ValueCheck> extends AbstractModelObjectDefinition implements AttributeDefinition<C> {

	private List<C> valueChecks;
	private List<AttributeDefault> attributeDefaults;

	@Override
	public List<C> getValueChecks() {
		return this.valueChecks;
	}

	@Override
	public void setValueChecks(List<C> valueChecks) {
		this.valueChecks = valueChecks;
	}

	@Override
	public List<AttributeDefault> getAttributeDefaults() {
		return this.attributeDefaults;
	}

	@Override
	public void setAttributeDefaults(List<AttributeDefault> attributeDefaults) {
		this.attributeDefaults = attributeDefaults;
	}

}
