package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.ITEM;
import static org.openforis.idm.metamodel.xml.IdmlConstants.ITEMS;



/**
 * 
 * @author G. Miceli
 * @author S. Ricci
 *
 */
class CodeListItemsPR extends IdmlPullReader {
	
	private boolean skipChildItems;

	public CodeListItemsPR() {
		this(false);
	}
	
	public CodeListItemsPR(boolean skipChildItems) {
		super(ITEMS, 1);
		this.skipChildItems = skipChildItems;
		addChildPullReaders(createNewItemPR());
	}

	protected XmlPullReader createNewItemPR() {
		if ( skipChildItems ) {
			return new SkipElementPR(ITEM);
		} else {
			return new CodeListItemPR();
		}
	}
}