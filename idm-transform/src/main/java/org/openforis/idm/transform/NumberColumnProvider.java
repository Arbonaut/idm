package org.openforis.idm.transform;

import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NumericAttributeDefinition;

/**
 * @author G. Miceli
 */
public class NumberColumnProvider extends AttributeColumnProvider {

	public NumberColumnProvider(NumericAttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup);
	}
	
	@Override
	protected ColumnProviderChain createFieldProviderChain() {
		ColumnProviderChain chain = new ColumnProviderChain(); 
		NumericAttributeDefinition defn = (NumericAttributeDefinition) getAttributeDefinition();
		// Only include qualifier and column group if qualifier is ever allowed   
		FieldDefinition valueFieldDefinition = defn.getFieldDefinition("value");
		if ( defn.isVariableUnit() ) {
			ColumnGroup columnGroup = new ColumnGroup(defn.getName(), null, defn, getParentGroup(), this); // TODO heading
			FieldColumnProvider codeColumnProvider = new FieldColumnProvider(valueFieldDefinition, columnGroup);
			chain.addProvider(codeColumnProvider);
			FieldDefinition unitFieldDefn = defn.getFieldDefinition("unit");
			FieldColumnProvider qualifierColumnProvider = new FieldColumnProvider(unitFieldDefn, columnGroup);
			chain.addProvider(qualifierColumnProvider);				
		} else {
			FieldColumnProvider codeColumnProvider = new FieldColumnProvider(valueFieldDefinition, getParentGroup(), defn.getName());
			chain.addProvider(codeColumnProvider);
		}
		return chain;
	}


}
