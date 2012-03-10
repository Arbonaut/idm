/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", "labels", "prompts", "descriptions", "childDefinitions" })
public class EntityDefinition extends NodeDefinition {

	private static final long serialVersionUID = 1L;
	
	@XmlElements({
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
	private List<NodeDefinition> childDefinitions;

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
		return null;
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
}
