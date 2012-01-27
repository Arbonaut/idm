package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.CodeList;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CodeTypeAdapter extends EnumAdapter<CodeList.CodeType> {

	public CodeTypeAdapter() {
		super(CodeList.CodeType.class);
	}
}
