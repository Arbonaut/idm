/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import org.apache.commons.jxpath.DynamicPropertyHandler;

/**
 * @author M. Togna
 * 
 */
public class SchemaPropertyHandler implements DynamicPropertyHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#getPropertyNames(java.lang.Object)
	 */
	@Override
	public String[] getPropertyNames(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#getProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getProperty(Object object, String propertyName) {
		if (object instanceof Schema) {
			Schema schema = (Schema) object;
			List<EntityDefinition> entityDefinitions = schema.getRootEntityDefinitions();
			for (EntityDefinition entityDefinition : entityDefinitions) {
				if (propertyName.equals(entityDefinition.getName())) {
					return entityDefinition;
				}
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#setProperty(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(Object object, String propertyName, Object value) {
		// TODO Auto-generated method stub

	}

}
