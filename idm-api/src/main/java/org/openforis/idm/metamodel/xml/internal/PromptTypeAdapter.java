package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.Prompt;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class PromptTypeAdapter extends EnumAdapter<Prompt.Type> {

	public PromptTypeAdapter() {
		super(Prompt.Type.class);
	}
}
