/**
 * 
 */
package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.NodeLabel;
import org.openforis.idm.metamodel.NumericAttributeDefinition;
import org.openforis.idm.metamodel.Prompt;
import org.openforis.idm.metamodel.TextAttributeDefinition;
import org.openforis.idm.metamodel.validation.Check;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

/**
 * @author S. Ricci
 *
 */
public class TransformMatcher implements Matcher {

	/* (non-Javadoc)
	 * @see org.simpleframework.xml.transform.Matcher#match(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Transform match(Class type) throws Exception {
        if (type.isEnum()) {
        	if ( type == Check.Flag.class ) {
        		return new CheckFlagTransform();
        	} else if ( type == CodeListLabel.Type.class) {
        		return new CodeListLabelTypeTransform();
        	} else if ( type == CodeList.CodeScope.class ) {
        		return new CodeScopeTransform();
        	} else if ( type == NodeLabel.Type.class ) {
        		return new NodeLabelTypeTransform();
        	} else if ( type == NumericAttributeDefinition.Type.class ) {
        		return new NumericAttributeDefinitionTypeTransform();
        	} else if ( type == Prompt.Type.class ) {
        		return new PromptTypeTransform();
        	} else if ( type == TextAttributeDefinition.Type.class ) {
        		return new TextAttributeDefinitionTypeTransform();
        	}
        }
        return null;
    }

}
