/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 */
public interface ExternalCodeListProvider {

	boolean isValid(CodeList codeList, CodeAttribute code);

}
