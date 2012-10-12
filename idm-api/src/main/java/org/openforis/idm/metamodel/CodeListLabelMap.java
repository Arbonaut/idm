package org.openforis.idm.metamodel;

/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListLabelMap extends TypedLanguageSpecificTextAbstractMap<CodeListLabel, CodeListLabel.Type> {
	
	public void add(CodeListLabel label) {
		super.add(label.getType(), label);
	}
	
}
