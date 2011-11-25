package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Expression {
	
	Object[] evaluate(Record context);
	
	Object[] evaluate(ModelObject<?,?> context);
	
}
