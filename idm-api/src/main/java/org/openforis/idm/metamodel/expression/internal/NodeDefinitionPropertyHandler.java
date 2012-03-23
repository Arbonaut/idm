/**
 * 
 */
package org.openforis.idm.metamodel.expression.internal;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author M. Togna
 * 
 */
public class NodeDefinitionPropertyHandler implements DynamicPropertyHandler {

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
			NodeDefinition nodeDefinition = (NodeDefinition) object;
			property = nodeDefinition.getParentDefinition();
		} else if (object instanceof EntityDefinition) {
			EntityDefinition entityDefinition = (EntityDefinition) object;
			property = entityDefinition.getChildDefinition(propertyName);
		} else if ((property == null) || (object instanceof AttributeDefinition)) {
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
