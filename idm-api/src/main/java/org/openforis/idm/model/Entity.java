/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 * 
 */
public class Entity extends ModelObject<EntityDefinition> {

	private Map<String, List<ModelObject<? extends SchemaObjectDefinition>>> childrenByName;

	public Entity(EntityDefinition definition) {
		super(definition);
		this.childrenByName = new HashMap<String, List<ModelObject<? extends SchemaObjectDefinition>>>();
	}

	/**
	 * @param name
	 * @param idx
	 * @return
	 * @throws ArrayIndexOutOfBoundsException if adding the Entity would break the maxCount rule
	 */
	public Entity addEntity(String name) {
		Entity entity = createEntity(name);
		addInternal(entity, null);
		return entity;
	}

	/**
	 * @param name
	 * @param idx
	 * @return
	 * @throws ArrayIndexOutOfBoundsException if adding the Entity would break the maxCount rule
	 */
	public Entity addEntity(String name, int idx) {
		Entity entity = createEntity(name);
		addInternal(entity, idx);
		return entity;
	}

	public AlphanumericCodeAttribute addValue(String name, AlphanumericCode value, int idx) {
		return (AlphanumericCodeAttribute) addValue(name, value, idx);
	}
	
	public AlphanumericCodeAttribute addValue(String name, AlphanumericCode value) {
		return (AlphanumericCodeAttribute) addValue(name, value, null);
	}
	
	public NumericCodeAttribute addValue(String name, NumericCode value, int idx) {
		return (NumericCodeAttribute) addValue(name, value, idx);
	}
	
	public NumericCodeAttribute addValue(String name, NumericCode value) {
		return (NumericCodeAttribute) addValue(name, value, null);
	}

	public RealAttribute addValue(String name, Double value, int idx) {
		return (RealAttribute) addValue(name, value, idx);
	}

	public RealAttribute addValue(String name, Double value) {
		return (RealAttribute) addValue(name, value, null);
	}

	public IntegerAttribute addValue(String name, Integer value, int idx) {
		return (IntegerAttribute) addValue(name, value, idx);
	}

	public IntegerAttribute addValue(String name, Integer value) {
		return (IntegerAttribute) addValue(name, value, null);
	}
	
	// TODO other addXXX and setXXX methods

	public ModelObject<? extends SchemaObjectDefinition> get(String name, int index) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = childrenByName.get(name);
		if (list == null) {
			return null;
		} else {
			return list.get(index);
		}
	}
/*
	public Set<String> getChildNames() {
		Set<String> childNames = childrenByName.keySet();
		return Collections.unmodifiableSet(childNames);
	}
*/
	public int getCount(String name) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = childrenByName.get(name);
		return list == null ? 0 : list.size();
	}

	public List<RuleFailure> getErrors(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RuleFailure> getWarnings(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasErrors(String name) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean hasWarnings(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRelevant(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequired(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public void move(String name, int oldIndex, int newIndex) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = childrenByName.get(name);
		if ( list != null ) {
			ModelObject<? extends SchemaObjectDefinition> obj = list.remove(oldIndex);
			list.add(newIndex, obj);
		}
//		updateList(newIndex, name);
	}

	
	public ModelObject<? extends SchemaObjectDefinition> remove(String name, int index) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = childrenByName.get(name);
		if ( list == null ) {
			return null;
		} else {
			ModelObject<? extends SchemaObjectDefinition> modelObject = list.remove(index);
//		this.updateList(index, name);
			return modelObject;
		}
	}


	private <V> Attribute<? extends AttributeDefinition, V> addValue(String name, V value, Integer idx) {
		Attribute<? extends AttributeDefinition, V> attr = createAttribute(name, value);
		addInternal(attr, idx);
		return attr;
	}

	/**
	 * Adds an item at the specified index.  Assumed o has already been checked to be of the
	 * appropriate type.  All added entities or attributes pass through this method
	 * @param o
	 * @param idx
	 * @throws ArrayIndexOutOfBoundsException if inserting object would break the maxCount rule
	 */
	private void addInternal(ModelObject<? extends SchemaObjectDefinition> o, Integer idx) {
		// Get and/or create list
		SchemaObjectDefinition defn = o.getDefinition();
		String name = defn.getName();
		
		List<ModelObject<? extends SchemaObjectDefinition>> children = childrenByName.get(name);
		if (children == null) {
			children = new ArrayList<ModelObject<? extends SchemaObjectDefinition>>();
			childrenByName.put(name, children);
		}

		// Check maxCount constraint
		int cnt = children.size();
		Integer max = defn.getMaxCount();
		if ( max != null && cnt >= max ) {
			throw new ArrayIndexOutOfBoundsException("At most "+max+" "+defn.getPath() + " allowed");
		}
		
		// Add item
		if ( idx == null ) {
			children.add(o);
		} else {
			children.add(idx, o);
		}
		o.setParent(this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <V> Attribute<? extends AttributeDefinition, V> createAttribute(String name, V value) {
		Attribute attr;
		if ( value instanceof AlphanumericCode ) {
			CodeAttributeDefinition defn = getChildDefinition(name, CodeAttributeDefinition.class);
			attr = new AlphanumericCodeAttribute(defn);
		} else if ( value instanceof NumericCode ) {
			CodeAttributeDefinition defn = getChildDefinition(name, CodeAttributeDefinition.class);
			attr = new NumericCodeAttribute(defn);
		} else if ( value instanceof Double ) {
			NumberAttributeDefinition defn = getChildDefinition(name, NumberAttributeDefinition.class);
			attr = new RealAttribute(defn);
		} else if ( value instanceof Integer ) {
			NumberAttributeDefinition defn = getChildDefinition(name, NumberAttributeDefinition.class);
			attr = new IntegerAttribute(defn);
		} else {
			throw new UnsupportedOperationException("createAttribute() for "+value.getClass().getName()+" value not implemented");
		}
		attr.setValue(value);
		return attr;
	}
	
	private Entity createEntity(String name) {
		EntityDefinition defn = getChildDefinition(name, EntityDefinition.class);
		Entity entity = new Entity(defn);
		return entity;
	}

	/**
	 * Get child definition
	 * @throws IllegalArgumentException if not defined in model
	 */
	private SchemaObjectDefinition getChildDefinition(String name) {
		SchemaObjectDefinition childDefinition = getDefinition().getChildDefinition(name);
		if ( childDefinition == null ) {
			throw new IllegalArgumentException("Undefined schema object "+getDefinition().getPath()+"/"+name);
		}
		return childDefinition;
	}

	/**
	 * Get child definition and cast to requested type
	 * @throws IllegalArgumentException if not defined in model or if not assignable from type defined in definitionClass
	 */
	@SuppressWarnings("unchecked")
	private <T> T getChildDefinition(String name, Class<T> definitionClass) {
		SchemaObjectDefinition childDefinition = getChildDefinition(name);
		if ( !childDefinition.getClass().isAssignableFrom(definitionClass) ) {
			throw new IllegalArgumentException(childDefinition.getPath()+" is not a "+definitionClass.getSimpleName());			
		}
		return (T) childDefinition;
	}

	public List<ModelObject<? extends SchemaObjectDefinition>> getChildren(String name) {
		List<ModelObject<? extends SchemaObjectDefinition>> children = childrenByName.get(name);
		return children == null ? null : Collections.unmodifiableList(children);
	}

	// 
	// public void clear(String name) {
	// this.children.remove(name);
	// }
	//
	// 
	// public void clear() {
	// this.children.clear();
	// }

	// 
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
		((ModelObject<? extends SchemaObjectDefinition>) o).setRecord(this.getRecord());
	}

	private void updateList(int fromIndex, String name) {
		List<ModelObject<? extends SchemaObjectDefinition>> list = this.children.get(name);
		for (int i = fromIndex; i < list.size(); i++) {
			ModelObject<? extends SchemaObjectDefinition> modelObject = (ModelObject<? extends SchemaObjectDefinition>) list.get(i);
			modelObject.setPath(this.getPath() + "/" + name + "[" + i + "]");
		}
	}
	*/
		
	@Override
	protected void write(StringWriter sw, int indent) {
		for (int i = 0; i < indent; i++) {
			sw.append('\t');
		}
		sw.append(getName());
		sw.append(":\n");
//		List<ModelObject<? extends SchemaObjectDefinition>> children = getChildren();
//		for (ModelObject<? extends SchemaObjectDefinition> child : children) {
		List<SchemaObjectDefinition> definitions = getDefinition().getChildDefinitions();
		for (SchemaObjectDefinition defn : definitions) {
			List<ModelObject<? extends SchemaObjectDefinition>> children = childrenByName.get(defn.getName());
			if ( children != null ) {
				for (ModelObject<? extends SchemaObjectDefinition> child : children) {
					child.write(sw, indent+1);
				}
			}
		}
//		}
	}

//	/**
//	 * 
//	 * @return Unmodifiable list of child instances, sorted by their schema order.
//	 */
//	public List<ModelObject<? extends SchemaObjectDefinition>> getChildren() {
//		List<ModelObject<? extends SchemaObjectDefinition>> result = new ArrayList<ModelObject<? extends SchemaObjectDefinition>>(); 
//		List<SchemaObjectDefinition> definitions = getDefinition().getChildDefinitions();
//		for (SchemaObjectDefinition defn : definitions) {
//			List<ModelObject<? extends SchemaObjectDefinition>> children = childrenByName.get(defn.getName());
//			if ( children != null ) {
//				for (ModelObject<? extends SchemaObjectDefinition> child : children) {
//					result.add(child);
//				}
//			}
//		}
//		return Collections.unmodifiableList(result);
//	}
}
