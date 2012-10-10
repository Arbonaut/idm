package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class EntityDefinitionPR extends NodeDefinitionPR {
	public EntityDefinitionPR() {
		super("entity");
		addChildPullReaders(
				this,
				new BooleanAttributeDefinitionPR(), 
				new CodeAttributeDefinitionPR(),
				new CoordinateAttributeDefinitionPR(),
				new DateAttributeDefinitionPR(),
				new TimeAttributeDefinitionPR(),
				new FileAttributeDefinitionPR(),
				new NumberAttributeDefinitionPR(),
				new RangeAttributeDefinitionPR(),
				new TaxonAttributeDefinitionPR(),
				new TextAttributeDefinitionPR());
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createEntityDefinition(id);
	}
}