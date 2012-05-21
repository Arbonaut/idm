package org.openforis.idm.transform;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;

/**
 * @author G. Miceli
 */
public class CodeColumnProvider extends AttributeColumnProvider {

	public CodeColumnProvider(AttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup);
	}
	
	@Override
	protected ColumnProviderChain createFieldProviderChain() {
		ColumnProviderChain chain = new ColumnProviderChain(); 
		CodeAttributeDefinition defn = (CodeAttributeDefinition) getAttributeDefinition();
		// Only include qualifier and column group if qualifier is ever allowed   
		CodeList list = defn.getList();
		if ( list.isQualifiable() ) {
			ColumnGroup columnGroup = new ColumnGroup(defn.getName(), null, defn, getParentGroup(), this); // TODO heading
			FieldColumnProvider codeColumnProvider = new FieldColumnProvider(defn.getFieldDefinition("code"), columnGroup);
			chain.addProvider(codeColumnProvider);
			FieldColumnProvider qualifierColumnProvider = new FieldColumnProvider(defn.getFieldDefinition("qualifier"), columnGroup);
			chain.addProvider(qualifierColumnProvider);				
		} else {
			FieldColumnProvider codeColumnProvider = new FieldColumnProvider(defn.getFieldDefinition("code"), getParentGroup(), defn.getName());
			chain.addProvider(codeColumnProvider);
		}
		return chain;
	}
}
