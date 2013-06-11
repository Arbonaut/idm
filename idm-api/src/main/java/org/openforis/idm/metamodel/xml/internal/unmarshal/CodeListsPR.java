package org.openforis.idm.metamodel.xml.internal.unmarshal;


import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class CodeListsPR extends IdmlPullReader {
	

	public CodeListsPR() {
		super(CODE_LISTS, 1);
		addChildPullReaders(createCodeListReader());
	}

	protected CodeListPR createCodeListReader() {
		return new CodeListPR();
	}
	
}