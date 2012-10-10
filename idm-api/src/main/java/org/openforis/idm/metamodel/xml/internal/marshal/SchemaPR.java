package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class SchemaPR extends IdmlPullReader {

	public SchemaPR() {
		super(SCHEMA);
		addChildPullReaders(new EntityDefinitionPR());
	}
}