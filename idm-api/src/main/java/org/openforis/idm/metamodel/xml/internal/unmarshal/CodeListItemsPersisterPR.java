package org.openforis.idm.metamodel.xml.internal.unmarshal;


/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListItemsPersisterPR extends CodeListItemsPR {

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends CodeListItemPR> T createNewItemPR() {
		return (T) new CodeListItemPersisterPR();
	}
}
