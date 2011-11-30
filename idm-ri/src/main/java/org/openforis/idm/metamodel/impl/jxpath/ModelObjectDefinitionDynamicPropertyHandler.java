/**
 * 
 */
package org.openforis.idm.metamodel.impl.jxpath;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.impl.AbstractAttributeDefinition;
import org.openforis.idm.metamodel.impl.EntityDefinitionImpl;

/**
 * @author M. Togna
 * 
 */
public class ModelObjectDefinitionDynamicPropertyHandler implements DynamicPropertyHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#getPropertyNames(java.lang.Object)
	 */
	@Override
	public String[] getPropertyNames(Object object) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#getProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getProperty(Object object, String propertyName) {
		Object property = null;
		if (propertyName.equals("__parent")) {
			ModelObjectDefinition modelObjectDefinition = (ModelObjectDefinition) object;
			property = modelObjectDefinition.getParentDefinition();
		} else if (object instanceof EntityDefinitionImpl) {
			EntityDefinitionImpl entityDefinition = (EntityDefinitionImpl) object;
			property = entityDefinition.getChildDefinition(propertyName);
		} else if ((property == null) || (object instanceof AbstractAttributeDefinition)) {
			try {
				property = PropertyUtils.getProperty(object, propertyName);
			} catch (Exception e) {
				return null;
			}
		}
		return property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.jxpath.DynamicPropertyHandler#setProperty(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(Object object, String propertyName, Object value) {
		// nothing...
	}

}
