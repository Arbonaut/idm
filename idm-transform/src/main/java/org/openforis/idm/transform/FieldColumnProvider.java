package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
public class FieldColumnProvider extends NodeColumnProvider {
	
	public FieldColumnProvider(NodeDefinition nodeDefinition) {
		super(nodeDefinition, null, null);
	}
	
	public FieldColumnProvider(NodeDefinition nodeDefinition,
			NodeColumnProvider parentProvider) {
		super(nodeDefinition, parentProvider, null);
	}

	public FieldColumnProvider(NodeDefinition nodeDefinition,
			NodeColumnProvider parentProvider,
			ChildExpansionFilter childExpansionFilter) {
		super(nodeDefinition, parentProvider, childExpansionFilter);
	}

	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		// no child providers
	}
}
