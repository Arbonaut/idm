package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 */
public class DetachedModelDefinitionException extends IllegalStateException {
	private static final long serialVersionUID = 1L;

	public DetachedModelDefinitionException(Class<?> definitionClass, Class<?> containerClass) {
		super(definitionClass.getName()+" not attached to "+containerClass.getName());
	}
}
