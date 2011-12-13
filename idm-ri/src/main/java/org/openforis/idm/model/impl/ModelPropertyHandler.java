/**
 * 
 */
package org.openforis.idm.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.ModelObject;

/**
 * @author M. Togna
 * 
 */
public class ModelPropertyHandler implements DynamicPropertyHandler {
            
	@Override
	public Object getProperty(Object object, String propertyName) {

		Object property = null;
		if (propertyName.equals("__parent")) {
			@SuppressWarnings("unchecked")
			ModelObject<? extends ModelObjectDefinition> o = (ModelObject<? extends ModelObjectDefinition>) object;
			property = o.getParent();
		} else if (object instanceof Entity) {
			Entity entity = (Entity) object;
			List<ModelObject<? extends ModelObjectDefinition>> list = new ArrayList<ModelObject<? extends ModelObjectDefinition>>();
			for (int i = 0; i < entity.getCount(propertyName); i++) {
				ModelObject<? extends ModelObjectDefinition> e = entity.get(propertyName, i);
				list.add(e);
			}
			property = Collections.unmodifiableList(list);
		} else if ((property == null) || (object instanceof Attribute)) {
			try {
				// property = PropertyUtils.getProperty(object, propertyName);
				property = object;
			} catch (Exception e) {
				return null;
			}
		}

		return property;
	}

	@Override
	public String[] getPropertyNames(Object object) {
		if (object instanceof Entity) {
			Set<String> childNames = ((Entity) object).getChildNames();
			String[] array = new String[childNames.size()];
			int i = 0;
			for (String string : childNames) {
				array[i++] = string;
			}
			return array;
		}
		return null;
	}

	@Override
	public void setProperty(Object object, String propertyName, Object value) {
		// TODO Auto-generated method stub
	}

}
