package org.openforis.idm.transform;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

/**
 * @author G. Miceli
 */
public class TaxonColumnProvider extends AttributeColumnProvider {

	public TaxonColumnProvider(TaxonAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
