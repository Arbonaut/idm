package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;

/**
 * Provider chain is created on instantiation based IDML schema 
 * 
 * Columns are generated one call to getColumns based on provider parameters
 * 
 * @author G. Miceli
 */
public class EntityColumnProvider extends NodeColumnProvider {

//	private static final Log LOG = LogFactory.getLog(EntityColumnProvider.class);
	
	public EntityColumnProvider(NodeDefinition nodeDefinition) {
		super(nodeDefinition, null, null);
	}
	
	public EntityColumnProvider(NodeDefinition nodeDefinition,
			NodeColumnProvider parentProvider) {
		super(nodeDefinition, parentProvider, null);
	}

	public EntityColumnProvider(NodeDefinition nodeDefinition, NodeColumnProvider parentProvider,
				ChildExpansionFilter childExpansionFilter) {
		super(nodeDefinition, parentProvider, childExpansionFilter);
	}

	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		EntityDefinition defn = (EntityDefinition) getNodeDefinition();
		List<NodeDefinition> children = defn.getChildDefinitions();
		for (NodeDefinition child : children) {
			NodeColumnProvider provider;
			if ( child instanceof EntityDefinition ) {
				provider = new EntityColumnProvider((EntityDefinition) child, this, null);
			} else if ( child instanceof AttributeDefinition ) {
				provider = new AttributeColumnProvider((AttributeDefinition) child, this, null);
			} else {
				throw new RuntimeException("Entity should never have child "+child.getClass());
			}
			providers.add(provider);
		}
	}
}

