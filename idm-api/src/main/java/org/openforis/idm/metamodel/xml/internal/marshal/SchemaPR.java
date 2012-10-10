package org.openforis.idm.metamodel.xml.internal.marshal;




/**
 * @author G. Miceli
 */
class SchemaPR extends IdmlPullReader {
	public SchemaPR() {
		super("schema");
		addChildPullReaders(new EntityDefinitionPR());
	}
}