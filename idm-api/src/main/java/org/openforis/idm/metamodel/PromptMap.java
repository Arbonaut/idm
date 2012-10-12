package org.openforis.idm.metamodel;

/**
 * 
 * @author S. Ricci
 *
 */
public class PromptMap extends TypedLanguageSpecificTextAbstractMap<Prompt, Prompt.Type> {

	public void add(Prompt prompt) {
		super.add(prompt.getType(), prompt);
	}
	
}
