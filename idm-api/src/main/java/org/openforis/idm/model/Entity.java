/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.DetachedNodeDefinitionException;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.FileAttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.TextAttributeDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 * 
 */
public class Entity extends Node<EntityDefinition> {

	private Map<String, List<Node<? extends NodeDefinition>>> childrenByName;

	public Entity(EntityDefinition definition) {
		super(definition);
		this.childrenByName = new HashMap<String, List<Node<? extends NodeDefinition>>>();
	}

	/**
	 * @param name
	 * @return the newly created Entity
	 * @throws ArrayIndexOutOfBoundsException
	 *             if adding would break the maxCount rule
	 */
	public Entity addEntity(String name) {
		Entity entity = createEntity(name);
		addInternal(entity, null);
		return entity;
	}

	/**
	 * @param name
	 * @param idx
	 * @return the newly created Entity
	 * @throws ArrayIndexOutOfBoundsException
	 *             if adding would break the maxCount rule
	 */
	public Entity addEntity(String name, int idx) {
		Entity entity = createEntity(name);
		addInternal(entity, idx);
		return entity;
	}

	// public <T extends Node<?>> T add(T o, int idx) {
	// return addInternal(o, idx);
	// }
	//
	// public <T extends Node<?>> T add(T o) {
	// return addInternal(o, null);
	// }

	// public Entity addEntity(Entity entity) {
	// addInternal(entity, null);
	// return entity;
	// }
	//
	// public Entity addEntity(Entity entity, int idx) {
	// addInternal(entity, idx);
	// return entity;
	// }

	// public <A extends Attribute<?,?>> A addAttribute(A attr) {
	// addInternal(attr, null);
	// return attr;
	// }
	//
	// public <A extends Attribute<?,?>> A addAttribute(A attr, int idx) {
	// addInternal(attr, idx);
	// return attr;
	// }

	// public <A extends Attribute<D, V>, D extends AttributeDefinition, V> A addXXX(String name, V value, int idx) {
	// return null;
	// }

	public AlphanumericCodeAttribute addValue(String name, AlphanumericCode value, int idx) {
		return addValueInternal(name, value, idx, AlphanumericCodeAttribute.class, CodeAttributeDefinition.class);
	}

	public AlphanumericCodeAttribute addValue(String name, AlphanumericCode value) {
		return addValueInternal(name, value, null, AlphanumericCodeAttribute.class, CodeAttributeDefinition.class);
	}

	public BooleanAttribute addValue(String name, Boolean value, int idx) {
		return addValueInternal(name, value, idx, BooleanAttribute.class, BooleanAttributeDefinition.class);
	}

	public BooleanAttribute addValue(String name, Boolean value) {
		return addValueInternal(name, value, null, BooleanAttribute.class, BooleanAttributeDefinition.class);
	}

	public CoordinateAttribute addValue(String name, Coordinate value, int idx) {
		return addValueInternal(name, value, idx, CoordinateAttribute.class, CoordinateAttributeDefinition.class);
	}

	public CoordinateAttribute addValue(String name, Coordinate value) {
		return addValueInternal(name, value, null, CoordinateAttribute.class, CoordinateAttributeDefinition.class);
	}

	public FileAttribute addValue(String name, File value, int idx) {
		return addValueInternal(name, value, idx, FileAttribute.class, FileAttributeDefinition.class);
	}

	public FileAttribute addValue(String name, File value) {
		return addValueInternal(name, value, null, FileAttribute.class, FileAttributeDefinition.class);
	}

	public NumericCodeAttribute addValue(String name, NumericCode value, int idx) {
		return addValueInternal(name, value, idx, NumericCodeAttribute.class, CodeAttributeDefinition.class);
	}

	public NumericCodeAttribute addValue(String name, NumericCode value) {
		return addValueInternal(name, value, null, NumericCodeAttribute.class, CodeAttributeDefinition.class);
	}

	public RealAttribute addValue(String name, Double value, int idx) {
		return addValueInternal(name, value, idx, RealAttribute.class, NumberAttributeDefinition.class);
	}

	public RealAttribute addValue(String name, Double value) {
		return addValueInternal(name, value, null, RealAttribute.class, NumberAttributeDefinition.class);
	}

	public IntegerAttribute addValue(String name, Integer value, int idx) {
		return addValueInternal(name, value, idx, IntegerAttribute.class, NumberAttributeDefinition.class);
	}

	public IntegerAttribute addValue(String name, Integer value) {
		return addValueInternal(name, value, null, IntegerAttribute.class, NumberAttributeDefinition.class);
	}

	public DateAttribute addValue(String name, Date value, int idx) {
		return addValueInternal(name, value, idx, DateAttribute.class, DateAttributeDefinition.class);
	}

	public DateAttribute addValue(String name, Date value) {
		return addValueInternal(name, value, null, DateAttribute.class, DateAttributeDefinition.class);
	}
	
	public TextAttribute addValue(String name, String value, int idx) {
		return addValueInternal(name, value, idx, TextAttribute.class, TextAttributeDefinition.class);
	}

	public TextAttribute addValue(String name, String value) {
		return addValueInternal(name, value, null, TextAttribute.class, TextAttributeDefinition.class);
	}

	public TimeAttribute addValue(String name, Time value, int idx) {
		return addValueInternal(name, value, idx, TimeAttribute.class, TimeAttributeDefinition.class);
	}

	public TimeAttribute addValue(String name, Time value) {
		return addValueInternal(name, value, null, TimeAttribute.class, TimeAttributeDefinition.class);
	}

	// TODO other addXXX and setXXX methods

	public Node<? extends NodeDefinition> get(String name, int index) {
		List<Node<? extends NodeDefinition>> list = childrenByName.get(name);
		if (list == null) {
			return null;
		} else {
			return list.get(index);
		}
	}

	/*
	 * public Set<String> getChildNames() { Set<String> childNames = childrenByName.keySet(); return Collections.unmodifiableSet(childNames); }
	 */
	public int getCount(String name) {
		List<Node<? extends NodeDefinition>> list = childrenByName.get(name);
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
		List<Node<? extends NodeDefinition>> list = childrenByName.get(name);
		if (list != null) {
			Node<? extends NodeDefinition> obj = list.remove(oldIndex);
			list.add(newIndex, obj);
		}
		// updateList(newIndex, name);
	}

	public Node<? extends NodeDefinition> remove(String name, int index) {
		List<Node<? extends NodeDefinition>> list = childrenByName.get(name);
		if (list == null) {
			return null;
		} else {
			Node<? extends NodeDefinition> node = list.remove(index);
			// this.updateList(index, name);
			return node;
		}
	}

	private <T extends Attribute<D, V>, D extends AttributeDefinition, V> T addValueInternal(String name, V value, Integer idx, Class<T> type, Class<D> definitionType) {
		T attr = createNode(name, type, definitionType);
		attr.setValue(value);
		addInternal(attr, idx);
		return attr;
	}

	/**
	 * Adds an item at the specified index. Assumed o has already been checked to be of the appropriate type. All added entities or attributes pass
	 * through this method
	 * 
	 * @param o
	 * @param idx
	 * @throws ArrayIndexOutOfBoundsException
	 *             if inserting object would break the maxCount rule
	 */
	private <T extends Node<?>> T addInternal(T o, Integer idx) {
		Record record = getRecord();
		if (record == null) {
			throw new DetachedNodeDefinitionException(o.getDefinition().getClass(), this.getClass());
		}

		// Get child definition and name
		NodeDefinition defn = o.getDefinition();
		String name = defn.getName();

		// Get child's definition and check schema object definition is the same
		NodeDefinition childDefn = getDefinition().getChildDefinition(name);
		if (defn != childDefn) {
			throw new IllegalArgumentException("Cannot add object; definitions do not match");
		}

		// Get or create list containing children
		List<Node<? extends NodeDefinition>> children = childrenByName.get(name);
		if (children == null) {
			children = new ArrayList<Node<? extends NodeDefinition>>();
			childrenByName.put(name, children);
		}

		// Check maxCount constraint
		int cnt = children.size();
		Integer max = defn.getMaxCount();
		if (max != null && cnt >= max) {
			throw new ArrayIndexOutOfBoundsException("At most " + max + " " + defn.getPath() + " allowed");
		}

		// Add item
		if (idx == null) {
			children.add(o);
		} else {
			children.add(idx, o);
		}

		o.setParent(this);

		return o;
	}

	// TODO Naming: Node vs Node?
	private <T extends Node<D>, D extends NodeDefinition> T createNode(String name, Class<T> type, Class<D> definitionType) {
		try {
			NodeDefinition definition = getChildDefinition(name, definitionType);
			return type.getConstructor(definitionType).newInstance(definition);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				throw new RuntimeException(e);
			}
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private Entity createEntity(String name) {
		EntityDefinition defn = getChildDefinition(name, EntityDefinition.class);
		Entity entity = new Entity(defn);
		return entity;
	}

	/**
	 * Get child definition
	 * 
	 * @throws IllegalArgumentException
	 *             if not defined in model
	 */
	private NodeDefinition getChildDefinition(String name) {
		NodeDefinition childDefinition = getDefinition().getChildDefinition(name);
		if (childDefinition == null) {
			throw new IllegalArgumentException("Undefined schema object " + getDefinition().getPath() + "/" + name);
		}
		return childDefinition;
	}

	/**
	 * Get child definition and cast to requested type
	 * 
	 * @throws IllegalArgumentException
	 *             if not defined in model or if not assignable from type defined in definitionClass
	 */
	@SuppressWarnings("unchecked")
	private <T> T getChildDefinition(String name, Class<T> definitionClass) {
		NodeDefinition childDefinition = getChildDefinition(name);
		if (!childDefinition.getClass().isAssignableFrom(definitionClass)) {
			throw new IllegalArgumentException(childDefinition.getPath() + " is not a " + definitionClass.getSimpleName());
		}
		return (T) childDefinition;
	}

	public List<Node<? extends NodeDefinition>> getAll(String name) {
		List<Node<? extends NodeDefinition>> children = childrenByName.get(name);
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
	// public Node<? extends NodeDefinition> set(Node<? extends NodeDefinition> o, int index) {
	// this.beforeUpdate(o);
	// String name = o.getDefinition().getName();
	// List<Node<? extends NodeDefinition>> list = this.get(name);
	// Node<? extends NodeDefinition> object = list.set(index, o);
	//
	// this.updateList(index, name);
	//
	// return object;
	// }

	/*
	 * private void beforeUpdate(Node<? extends NodeDefinition> o) { ((Node<? extends NodeDefinition>) o).setRecord(this.getRecord()); }
	 * 
	 * private void updateList(int fromIndex, String name) { List<Node<? extends NodeDefinition>> list = this.children.get(name); for (int i =
	 * fromIndex; i < list.size(); i++) { Node<? extends NodeDefinition> node = (Node<? extends NodeDefinition>) list.get(i);
	 * node.setPath(this.getPath() + "/" + name + "[" + i + "]"); } }
	 */

	@Override
	protected void write(StringWriter sw, int indent) {
		for (int i = 0; i < indent; i++) {
			sw.append('\t');
		}
		sw.append(getName());
		sw.append(":\n");
		// List<Node<? extends NodeDefinition>> children = getChildren();
		// for (Node<? extends NodeDefinition> child : children) {
		List<NodeDefinition> definitions = getDefinition().getChildDefinitions();
		for (NodeDefinition defn : definitions) {
			List<Node<? extends NodeDefinition>> children = childrenByName.get(defn.getName());
			if (children != null) {
				for (Node<? extends NodeDefinition> child : children) {
					child.write(sw, indent + 1);
				}
			}
		}
		// }
	}

	// Pre-order depth-first traversal from here down
	public void traverse(NodeVisitor visitor) {
		// Initialize stack with root entity
		NodeStack stack = new NodeStack(this);
		// While there are still nodes to insert
		while (!stack.isEmpty()) {
			// Pop the next list of nodes to insert
			List<Node<? extends NodeDefinition>> nodes = stack.pop();
			// Insert this list in order
			for (int i = 0; i < nodes.size(); i++) {
				Node<? extends NodeDefinition> node = nodes.get(i);

				visitor.visit(node, i);

				// For entities, add existing child nodes to the stack
				if (node instanceof Entity) {
					Entity entity = (Entity) node;
					List<NodeDefinition> childDefns = entity.getDefinition().getChildDefinitions();
					for (NodeDefinition childDefn : childDefns) {
						List<Node<? extends NodeDefinition>> children = entity.getAll(childDefn.getName());
						if (children != null) {
							stack.push(children);
						}
					}
				}
			}
		}
	}

	private class NodeStack extends Stack<List<Node<? extends NodeDefinition>>> {
		private static final long serialVersionUID = 1L;

		public NodeStack(Entity root) {
			ArrayList<Node<? extends NodeDefinition>> rootList = new ArrayList<Node<? extends NodeDefinition>>(1);
			rootList.add(root);
			push(rootList);
		}
	}

	// /**
	// *
	// * @return Unmodifiable list of child instances, sorted by their schema order.
	// */
	// public List<Node<? extends NodeDefinition>> getChildren() {
	// List<Node<? extends NodeDefinition>> result = new ArrayList<Node<? extends NodeDefinition>>();
	// List<NodeDefinition> definitions = getDefinition().getChildDefinitions();
	// for (NodeDefinition defn : definitions) {
	// List<Node<? extends NodeDefinition>> children = childrenByName.get(defn.getName());
	// if ( children != null ) {
	// for (Node<? extends NodeDefinition> child : children) {
	// result.add(child);
	// }
	// }
	// }
	// return Collections.unmodifiableList(result);
	// }
}
