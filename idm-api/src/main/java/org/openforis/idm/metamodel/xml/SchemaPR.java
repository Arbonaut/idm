package org.openforis.idm.metamodel.xml;



/**
 * @author G. Miceli
 */
public class SchemaPR extends IdmlPullReader {
	public SchemaPR() {
		super("schema");
		setChildPullReaders(new EntityDefinitionPR());
	}
}