package org.openforis.idm.transform2;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NodeDefinition;

/** 
 * Base class provides one more columns for an attribute
 *  
 * @author G. Miceli
 *
 */
public class AttributeColumnProvider extends NodeColumnProvider {
	
	public AttributeColumnProvider(NodeDefinition nodeDefinition) {
		super(nodeDefinition, null, null);
	}
	
	public AttributeColumnProvider(NodeDefinition nodeDefinition,
			NodeColumnProvider parentProvider) {
		super(nodeDefinition, parentProvider, null);
	}
	
	public AttributeColumnProvider(NodeDefinition nodeDefinition,
			NodeColumnProvider parentProvider,
			ChildExpansionFilter childExpansionFilter) {
		super(nodeDefinition, parentProvider, childExpansionFilter);
	}

	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		AttributeDefinition defn = (AttributeDefinition) getNodeDefinition();
		List<FieldDefinition<?>> fields = defn.getFieldDefinitions();
		for (FieldDefinition<?> field : fields) {
			if ( isIncluded(field) ) {
				FieldColumnProvider provider = new FieldColumnProvider(field, this, null);
				providers.add(provider);
			}
		}
	}
}
