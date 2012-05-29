package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.FieldDefinition;

/** 
 * Base class provides one more columns for an attribute
 *  
 * @author G. Miceli
 *
 */
public abstract class AttributeColumnProvider extends NodeColumnProvider {

	public AttributeColumnProvider(AttributeDefinition attributeDefinition, NodeColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
	
	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		AttributeDefinition defn = (AttributeDefinition) getNodeDefinition();
		List<FieldDefinition<?>> fields = defn.getFieldDefinitions();
		for (FieldDefinition<?> field : fields) {
			if ( isIncluded(field) ) {
				FieldColumnProvider provider = new FieldColumnProvider(field, this);
				providers.add(provider);
			}
		}
	}

	/**
	 * Override this method to conditionally exclude fields
	 * @param fieldDefinition
	 * @return
	 */
	protected boolean isIncluded(FieldDefinition<?> fieldDefinition) {
		return true;
	}
}
