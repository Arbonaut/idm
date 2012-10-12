package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * 
 * @author G. Miceli
 * 
 */
public class EntityDefinitionIM extends AbstractNodeDefinitionIM<EntityDefinition, Schema> {
	public EntityDefinitionIM() {
		super(ENTITY);
		addChildMarshallers(new NodeDefinitionIM(this));
	}
	
	/**
	 * Only used by SchemaIM.  NodeDefinition finds children on its own
	 */
	@Override
	protected void marshalInstances(Schema schema) throws IOException {
		List<EntityDefinition> roots = schema.getRootEntityDefinitions();
		marshal(roots);
	}
}
