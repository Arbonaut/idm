/**
 * 
 */
package org.openforis.idm.model.jxpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

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
			Node<? extends NodeDefinition> o = (Node<? extends NodeDefinition>) object;
			property = o.getParent();
		} else if (object instanceof Entity) {
			Entity entity = (Entity) object;
			List<Node<? extends NodeDefinition>> list = new ArrayList<Node<? extends NodeDefinition>>();
			for (int i = 0; i < entity.getCount(propertyName); i++) {
				Node<? extends NodeDefinition> e = entity.get(propertyName, i);
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
			Entity entity = (Entity) object;
			EntityDefinition entityDef = entity.getDefinition();
			List<NodeDefinition> childDefs = entityDef.getChildDefinitions();
//			Set<String> childNames = ((Entity) object).getChildNames();
			String[] array = new String[childDefs.size()];
			int i = 0;
			for (NodeDefinition def : childDefs) {
				array[i++] = def.getName();
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
