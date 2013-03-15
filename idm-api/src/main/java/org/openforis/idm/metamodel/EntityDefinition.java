/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class EntityDefinition extends NodeDefinition {

	private static final long serialVersionUID = 1L;
	
	private List<NodeDefinition> childDefinitions;

	EntityDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public List<NodeDefinition> getChildDefinitions() {
		return CollectionUtils.unmodifiableList(childDefinitions);
	}
	
	public NodeDefinition getChildDefinition(String name) {
		if (childDefinitions != null) {
			for (NodeDefinition childDefinition : childDefinitions) {
				if ( StringUtils.equals(childDefinition.getName(), name) ) {
					return childDefinition;
				}
			}
		}
		throw new IllegalArgumentException("Child definition " + name + " not found in " + getPath());
	}
	
	public NodeDefinition getChildDefinition(int id) {
		if (childDefinitions != null) {
			for (NodeDefinition childDefinition : childDefinitions) {
				if (childDefinition.getId() == id) {
					return childDefinition;
				}
			}
		}
		throw new IllegalArgumentException("Child definition with id " + id + 
				" not found in " + getPath());
	}

	/**
	 * Get child definition and cast to requested type
	 * 
	 * @throws IllegalArgumentException if not defined in model or if not 
	 * assignable from type defined in definitionClass
	 */
	@SuppressWarnings("unchecked")
	public <T extends NodeDefinition> T getChildDefinition(String name, Class<T> definitionClass) {
		NodeDefinition childDefinition = getChildDefinition(name);
		if (!childDefinition.getClass().isAssignableFrom(definitionClass)) {
			throw new IllegalArgumentException(childDefinition.getPath() + 
					" is not a " + definitionClass.getSimpleName());
		}
		return (T) childDefinition;
	}
	
	public int getChildDefinitionIndex(NodeDefinition defn) {
		if ( childDefinitions != null ) {
			int result = childDefinitions.indexOf(defn);
			if ( result < 0 ) {
				throw new IllegalArgumentException(this.getPath() + "- child not found:" + defn.getName());
			}
			return result;
		} else {
			throw new IllegalArgumentException(this.getPath() + " has no children");
		}
	}

	public void addChildDefinition(NodeDefinition defn) {
		if ( defn.getName() == null ) {
			throw new NullPointerException("name");
		}

		if ( defn.isDetached() ) {
			throw new IllegalArgumentException("Detached definitions cannot be added");
		}
		
		if (childDefinitions == null) {
			childDefinitions = new ArrayList<NodeDefinition>();
		}
		childDefinitions.add(defn);
		defn.setParentDefinition(this);
	}

	public void removeChildDefinition(int id) {
		NodeDefinition childDefn = getChildDefinition(id);
		removeChildDefinition(childDefn);
	}
	
	public void removeChildDefinition(NodeDefinition defn) {
		childDefinitions.remove(defn);
		defn.detach();
	}
	
	public void moveChildDefinition(int id, int index) {
		NodeDefinition defn = getChildDefinition(id);
		moveChildDefinition(defn, index);
	}

	public void moveChildDefinition(NodeDefinition defn, int newIndex) {
		CollectionUtils.shiftItem(childDefinitions, defn, newIndex);
	}
	
	public List<AttributeDefinition> getKeyAttributeDefinitions() {
		ArrayList<AttributeDefinition> result = new ArrayList<AttributeDefinition>();
		if ( childDefinitions != null ) {
			for (NodeDefinition nodeDefinition : childDefinitions) {
				if(nodeDefinition instanceof KeyAttributeDefinition) {
					KeyAttributeDefinition keyAttributeDefinition = (KeyAttributeDefinition) nodeDefinition;
					if(keyAttributeDefinition.isKey()) {
						result.add((AttributeDefinition) keyAttributeDefinition);
					}
				}
			}
		}
		return Collections.unmodifiableList(result);
	}

	// Pre-order depth-first traversal from here down
	public void traverse(NodeDefinitionVisitor visitor) {
		// Initialize stack with root entity
		Stack<NodeDefinition> stack = new Stack<NodeDefinition>();
		stack.push(this);
		// While there are still nodes to insert
		while (!stack.isEmpty()) {
			// Pop the next list of nodes to insert
			NodeDefinition defn = stack.pop();
			// Insert this list in order

			visitor.visit(defn);

			// For entities, add existing child nodes to the stack
			if (defn instanceof EntityDefinition) {
				EntityDefinition entityDefn = (EntityDefinition) defn;
				List<NodeDefinition> childDefns = entityDefn.getChildDefinitions();
				for (NodeDefinition childDefn : childDefns) {
					stack.push(childDefn);
				}
			}
		}		
	}

	@Override
	public Node<?> createNode() {
		return new Entity(this);
	}
	
	@Override
	public void detach() {
		for (NodeDefinition child : childDefinitions) {
			child.detach();
		}
		super.detach();
	}

	/**
	 *  
	 * @return true if entities with only keys of type internal code (not lookup)
	 */
	public boolean isEnumerable() {
		CodeAttributeDefinition enumeratingKeyCodeAttribute = getEnumeratingKeyCodeAttribute();
		return enumeratingKeyCodeAttribute != null;
	}
	
	public CodeAttributeDefinition getEnumeratingKeyCodeAttribute() {
		return getEnumeratingKeyCodeAttribute(null);
	}
	
	public CodeAttributeDefinition getEnumeratingKeyCodeAttribute(ModelVersion version) {
		List<AttributeDefinition> keyDefns = getKeyAttributeDefinitions();
		for (AttributeDefinition keyDefn: keyDefns) {
			if (version == null || version.isApplicable(keyDefn) ) {
				if ( keyDefn instanceof CodeAttributeDefinition ) {
					CodeAttributeDefinition codeDefn = (CodeAttributeDefinition) keyDefn;
					CodeList list = codeDefn.getList();
					if ( list.getLookupTable() == null ) {
						return codeDefn;
					}
				} else {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childDefinitions == null) ? 0 : childDefinitions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityDefinition other = (EntityDefinition) obj;
		if (childDefinitions == null) {
			if (other.childDefinitions != null)
				return false;
		} else if (!childDefinitions.equals(other.childDefinitions))
			return false;
		return true;
	}
	
}
