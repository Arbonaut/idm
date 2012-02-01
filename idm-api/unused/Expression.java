package org.openforis.idm.model;

import java.util.Iterator;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Expression {

	//?
	Object evaluate(Record context);

	Object evaluate(Node<? extends NodeDefinition> context);

	Iterator<?> Iterate(Node<? extends NodeDefinition> context);
	
}
