package org.openforis.idm.transform;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;

public class CodeColumnProvider extends AttributeColumnProvider {

	public CodeColumnProvider(AttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup, false);
	}
	
	@Override
	protected ColumnProviderChain createFieldProviderChain() {
		ColumnProviderChain chain = new ColumnProviderChain(); 
		CodeAttributeDefinition defn = (CodeAttributeDefinition) getAttributeDefinition();
		ColumnGroup columnGroup = new ColumnGroup(defn.getName(), null, defn, getParentGroup()); // TODO heading
		FieldColumnProvider codeColumnProvider = new FieldColumnProvider(defn.getFieldDefinition("code"), columnGroup);
		chain.addProvider(codeColumnProvider);
		CodeList list = defn.getList();
		if ( list.isQualifiable() ) {
			FieldColumnProvider qualifierColumnProvider = new FieldColumnProvider(defn.getFieldDefinition("qualifier"), columnGroup);
			chain.addProvider(qualifierColumnProvider);				
		}
		return chain;
	}
}
