/**
 * 
 */
package org.openforis.idm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author M. Togna
 * 
 */
public class DefaultEntity extends AbstractModelObject<EntityDefinition> implements Entity {

	private Map<String, List<ModelObject<? extends SchemaObjectDefinition>>> children;

	public DefaultEntity() {
		this.children = new HashMap<String, List<ModelObject<? extends SchemaObjectDefinition>>>();
	}

	@Override
	public ModelObject<? extends SchemaObjectDefinition> get(String name, int index) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		if (list != null) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public void add(ModelObject<? extends SchemaObjectDefinition> o) {
//		this.beforeUpdate(o);

		String name = o.getDefinition().getName();
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		if (list == null) {
			list = new ArrayList<ModelObject<? extends SchemaObjectDefinition>>();
			this.children.put(name, list);
		}
		list.add(o);

//		this.updateList(list.size() - 1, name);
	}

	@Override
	public void add(ModelObject<? extends SchemaObjectDefinition> o, int index) {
//		this.beforeUpdate(o);

		String name = o.getDefinition().getName();
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		list.add(index, o);

//		this.updateList(index, name);
	}

	@Override
	public int getCount(String name) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		return list != null ? list.size() : 0;
	}

	@Override
	public void move(String name, int oldIndex, int newIndex) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		ModelObject<? extends SchemaObjectDefinition> obj = list.remove(oldIndex);
		list.add(newIndex, obj);
//		updateList(newIndex, name);
	}

	@Override
	public ModelObject<? extends SchemaObjectDefinition> remove(String name, int index) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		ModelObject<? extends SchemaObjectDefinition> modelObject = list.remove(index);

//		this.updateList(index, name);

		return modelObject;
	}

	public Set<String> getChildNames() {
		Set<String> childNames = children.keySet();
		return Collections.unmodifiableSet(childNames);
	}

	@Override
	public List<RuleFailure> getErrors(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RuleFailure> getWarnings(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasErrors(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasWarnings(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequired(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRelevant(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	// @Override
	// public void clear(String name) {
	// this.children.remove(name);
	// }
	//
	// @Override
	// public void clear() {
	// this.children.clear();
	// }

	// @Override
	// public ModelObject<? extends SchemaObjectDefinition> set(ModelObject<? extends SchemaObjectDefinition> o, int index) {
	// this.beforeUpdate(o);
	// String name = o.getDefinition().getName();
	// List<ModelObject<? extends SchemaObjectDefinition>> list = this.get(name);
	// ModelObject<? extends SchemaObjectDefinition> object = list.set(index, o);
	//
	// this.updateList(index, name);
	//
	// return object;
	// }

/*	private void beforeUpdate(ModelObject<? extends SchemaObjectDefinition> o) {
		((AbstractModelObject<? extends SchemaObjectDefinition>) o).setRecord(this.getRecord());
	}

	private void updateList(int fromIndex, String name) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		for (int i = fromIndex; i < list.size(); i++) {
			AbstractModelObject<? extends SchemaObjectDefinition> modelObject = (AbstractModelObject<? extends SchemaObjectDefinition>) list.get(i);
			modelObject.setPath(this.getPath() + "/" + name + "[" + i + "]");
		}
	}
	*/

}
