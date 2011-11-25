package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObjectVisitor {
	void visit(Entity target);
	void visit(Attribute<?,?> target);
}
