package org.openforis.idm.metamodel.xml.internal.unmarshal;


/**
 * 
 * @author S. Ricci
 *
 */
public class CodeListPersisterPR extends CodeListPR {

	@Override
	protected CodeListItemsPR createCodeListItemsPR() {
		return new CodeListItemsPersisterPR();
	}

}
