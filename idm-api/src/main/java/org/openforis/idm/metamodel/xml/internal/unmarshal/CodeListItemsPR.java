package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.ITEMS;



/**
 * 
 * @author G. Miceli
 * @author S. Ricci
 *
 */
class CodeListItemsPR extends IdmlPullReader {
	
	public CodeListItemsPR() {
		super(ITEMS, 1);
		addChildPullReaders(createNewItemPR());
	}

	@SuppressWarnings("unchecked")
	protected <T extends CodeListItemPR> T createNewItemPR() {
		return (T) new CodeListItemPR();
	}
}