package org.openforis.idm.metamodel;

/**
 * 
 * @author M. Togna
 * 
 */
public interface ModelObjectLabel extends LanguageSpecificText {

	public enum LabelType {
		INSTANCE, LIST, PROMPT, NUMBER;
	}

	LabelType getLabelType();

	void setLabelType(LabelType labelType);

}