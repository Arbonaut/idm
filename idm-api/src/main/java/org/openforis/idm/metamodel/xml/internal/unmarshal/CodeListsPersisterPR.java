/**
 * 
 */
package org.openforis.idm.metamodel.xml.internal.unmarshal;




/**
 * @author S. Ricci
 *
 */
public class CodeListsPersisterPR extends CodeListsPR {

	public CodeListsPersisterPR() {
		super();
	}
	
	@Override
	protected CodeListPR createCodeListReader() {
		return new CodeListPersisterPR();
	}
	
}
