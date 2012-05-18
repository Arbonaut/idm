package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * Provider chain is created on instantiation based IDML schema 
 * 
 * Columns are generated one call to getColumns based on provider parameters
 */
public class EntityColumnProvider extends ColumnProviderChain {

	private EntityDefinition entityDefinition;
 
	// TODO iterate ancestor ids and add column providers
	// TODO iterate child definitions and add column providers 
	
	/**
	 * 
	 * @param entityDefinition
	 * @param parentGroup 
	 */
	public EntityColumnProvider(EntityDefinition entityDefinition, ColumnGroup parentGroup) {
		super();
		this.entityDefinition = entityDefinition;
		for (NodeDefinition defn : entityDefinition.getChildDefinitions()) {
			if ( defn.isMultiple() ) {
				// TODO
			} else {
				if (defn instanceof EntityDefinition) {
					String name = defn.getName();
					ColumnGroup columnGroup = new ColumnGroup(name, null, defn, parentGroup); // TODO heading
					EntityColumnProvider ecp = new EntityColumnProvider((EntityDefinition) defn, columnGroup);
					addProvider(ecp);
				} else if (defn instanceof AttributeDefinition) {
					AttributeColumnProvider acp = AttributeColumnProvider.createInstance((AttributeDefinition) defn, parentGroup);
					addProvider(acp);
				}
			}
		}
	}
	
	@Override
	public List<Cell> getCells(Node<?> parent) {
		if ( parent == null ) {
			return getEmptyCells();
		} else if ( parent instanceof Entity ) {
			Entity parentEntity = (Entity) parent;
			String name = entityDefinition.getName();
			Node<? extends NodeDefinition> node = parentEntity.get(name, 0);
			return super.getCells(node);
		} else {
			throw new IllegalArgumentException("Expected "+Entity.class+" but got "+parent.getClass());
		}
	}

	public EntityDefinition getEntityDefinition() {
		return entityDefinition;
	}
}
