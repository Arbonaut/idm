package org.openforis.idm.metamodel.xml.internal.unmarshal;


/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListItemsPersisterPR extends CodeListItemsPR {

	@Override
	protected XmlPullReader createNewItemPR() {
		return new CodeListItemPersisterPR();
	}
}
