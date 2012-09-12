/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Order;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 * @author S. Ricci
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes={"id", "name", "relevant","required", "requiredIf", "multiple", "minCount", "maxCount", "since", "deprecated"}, 
elements = {"label", "prompt", "description", "entity", "number", "range", "boolean", "date", "time", "file", "taxon", "coordinate", "code", "text"  })
public class EntityDefinition extends NodeDefinition {

	private static final long serialVersionUID = 1L;
	
	private List<NodeDefinition> childDefinitions;

	/*@XmlElements({
		@XmlElement(name = "entity",     type = EntityDefinition.class), 
		@XmlElement(name = "number",     type = NumberAttributeDefinition.class),
		@XmlElement(name = "range",      type = RangeAttributeDefinition.class), 
		@XmlElement(name = "boolean",    type = BooleanAttributeDefinition.class),
		@XmlElement(name = "date",       type = DateAttributeDefinition.class), 
		@XmlElement(name = "time",       type = TimeAttributeDefinition.class),
		@XmlElement(name = "file",       type = FileAttributeDefinition.class), 
		@XmlElement(name = "taxon",      type = TaxonAttributeDefinition.class),
		@XmlElement(name = "coordinate", type = CoordinateAttributeDefinition.class), 
		@XmlElement(name = "code",       type = CodeAttributeDefinition.class),
		@XmlElement(name = "text",       type = TextAttributeDefinition.class) })
	private List<NodeDefinition> childDefinitions;*/
	@SuppressWarnings("unused")
	@ElementListUnion({
		@ElementList(entry = "entity",     inline=true, required=false, type = EntityDefinition.class), 
		@ElementList(entry = "number",     inline=true, required=false, type = NumberAttributeDefinition.class),
		@ElementList(entry = "range",      inline=true, required=false, type = RangeAttributeDefinition.class), 
		@ElementList(entry = "boolean",    inline=true, required=false, type = BooleanAttributeDefinition.class),
		@ElementList(entry = "date",       inline=true, required=false, type = DateAttributeDefinition.class), 
		@ElementList(entry = "time",       inline=true, required=false, type = TimeAttributeDefinition.class),
		@ElementList(entry = "file",       inline=true, required=false, type = FileAttributeDefinition.class), 
		@ElementList(entry = "taxon",      inline=true, required=false, type = TaxonAttributeDefinition.class),
		@ElementList(entry = "coordinate", inline=true, required=false, type = CoordinateAttributeDefinition.class), 
		@ElementList(entry = "code",       inline=true, required=false, type = CodeAttributeDefinition.class),
		@ElementList(entry = "text",       inline=true, required=false, type = TextAttributeDefinition.class)
	})
	private void setChildDefinitionsInternal(List<NodeDefinition> childDefinitions) {
		for (NodeDefinition def : childDefinitions) {
			def.setParentDefinition(this);
		}
		this.childDefinitions = childDefinitions;
	}
	
	@SuppressWarnings("unused")
	@ElementListUnion({
		@ElementList(entry = "entity",     inline=true, required=false, type = EntityDefinition.class), 
		@ElementList(entry = "number",     inline=true, required=false, type = NumberAttributeDefinition.class),
		@ElementList(entry = "range",      inline=true, required=false, type = RangeAttributeDefinition.class), 
		@ElementList(entry = "boolean",    inline=true, required=false, type = BooleanAttributeDefinition.class),
		@ElementList(entry = "date",       inline=true, required=false, type = DateAttributeDefinition.class), 
		@ElementList(entry = "time",       inline=true, required=false, type = TimeAttributeDefinition.class),
		@ElementList(entry = "file",       inline=true, required=false, type = FileAttributeDefinition.class), 
		@ElementList(entry = "taxon",      inline=true, required=false, type = TaxonAttributeDefinition.class),
		@ElementList(entry = "coordinate", inline=true, required=false, type = CoordinateAttributeDefinition.class), 
		@ElementList(entry = "code",       inline=true, required=false, type = CodeAttributeDefinition.class),
		@ElementList(entry = "text",       inline=true, required=false, type = TextAttributeDefinition.class)
	})
	private List<NodeDefinition> getChildDefinitionsInternal() {
		return childDefinitions;
	}

	public List<NodeDefinition> getChildDefinitions() {
		return CollectionUtil.unmodifiableList(childDefinitions);
	}
	
	public NodeDefinition getChildDefinition(String name) {
		if (childDefinitions != null) {
			for (NodeDefinition childDefinition : childDefinitions) {
				if (childDefinition.getName().equals(name)) {
					return childDefinition;
				}
			}
		}
		throw new IllegalArgumentException("Child definition " + name + " not found in " + getPath());
	}
	
	/**
	 * Get child definition and cast to requested type
	 * 
	 * @throws IllegalArgumentException
	 *             if not defined in model or if not assignable from type defined in definitionClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T getChildDefinition(String name, Class<T> definitionClass) {
		NodeDefinition childDefinition = getChildDefinition(name);
		if (!childDefinition.getClass().isAssignableFrom(definitionClass)) {
			throw new IllegalArgumentException(childDefinition.getPath() + " is not a " + definitionClass.getSimpleName());
		}
		return (T) childDefinition;
	}

	public void addChildDefinition(NodeDefinition defn) {
		checkLockState();
		if (childDefinitions == null) {
			childDefinitions = new ArrayList<NodeDefinition>();
		}
		childDefinitions.add(defn);
	}

	public List<AttributeDefinition> getKeyAttributeDefinitions() {
		ArrayList<AttributeDefinition> result = new ArrayList<AttributeDefinition>();
		for (NodeDefinition nodeDefinition : childDefinitions) {
			if(nodeDefinition instanceof KeyAttributeDefinition) {
				KeyAttributeDefinition keyAttributeDefinition = (KeyAttributeDefinition) nodeDefinition;
				if(keyAttributeDefinition.isKey()) {
					result.add((AttributeDefinition) keyAttributeDefinition);
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
	
	/**
	 *  
	 * @return true if entities with only keys of type internal code (not lookup)
	 */
	public boolean isEnumerable() {
		List<AttributeDefinition> keyDefs = getKeyAttributeDefinitions();
		if ( keyDefs.isEmpty() ) {
			return false;
		} else {
			for (AttributeDefinition keyDef : keyDefs) {
				if ( keyDef instanceof CodeAttributeDefinition ) {
					CodeList list = ((CodeAttributeDefinition) keyDef).getList();
					if ( list.getLookupTable() != null ) {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		}
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
