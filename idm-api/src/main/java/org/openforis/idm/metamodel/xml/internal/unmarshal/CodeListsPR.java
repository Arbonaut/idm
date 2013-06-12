package org.openforis.idm.metamodel.xml.internal.unmarshal;


import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class CodeListsPR extends IdmlPullReader {
	
	private boolean skipChildItems;

	public CodeListsPR() {
		this(false);
	}
	
	public CodeListsPR(boolean skipChildItems) {
		super(CODE_LISTS, 1);
		this.skipChildItems = skipChildItems;
		addChildPullReaders(createCodeListReader());
	}

	protected CodeListPR createCodeListReader() {
		return new CodeListPR(skipChildItems);
	}
	
}