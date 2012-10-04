/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.xml.internal.XmlInit;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rootEntityDefinitions" })
public class Schema extends SurveyObject {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "entity", type = EntityDefinition.class)
	private List<EntityDefinition> rootEntityDefinitions;

	@XmlTransient
	private Map<String, NodeDefinition> definitionsByPath;

	@XmlTransient
	private Map<Integer, NodeDefinition> definitionsById;
	
	@XmlTransient
	private int lastDefinitionId;
	
	public Schema(Survey survey) {
		super(survey);
		definitionsByPath = new HashMap<String, NodeDefinition>(); 
		definitionsById = new HashMap<Integer, NodeDefinition>();
		lastDefinitionId = 0;
	}
	
	public NodeDefinition getByPath(String absolutePath) {
		return definitionsByPath.get(absolutePath);
	}
	
	public NodeDefinition getById(int id) {
		return definitionsById.get(id);
	}
	
	@XmlInit
	void reindexDefinitions() {
		 definitionsById.clear();
		 definitionsByPath.clear();
		 for (EntityDefinition entityDefn : getRootEntityDefinitions()) {
			entityDefn.traverse(new NodeDefinitionVisitor() {
				@Override
				public void visit(NodeDefinition definition) {
					indexByPath(definition);
					indexById(definition);
				}
			});
		}
	}

	void indexByPath(NodeDefinition definition) {
		indexByPath(definition, false);
	}
	
	void indexByPath(NodeDefinition definition, boolean indexChildren) {
		if ( definition.getName() != null ) {
			String path = definition.getPath();
			definitionsByPath.put(path, definition);
			if ( indexChildren && definition instanceof EntityDefinition ) {
				((EntityDefinition) definition).traverse(new NodeDefinitionVisitor() {
					@Override
					public void visit(NodeDefinition descendant) {
						String path = descendant.getPath();
						definitionsByPath.put(path, descendant);
					}
				});
			}
		}
	}

	void removeIndexByPath(NodeDefinition definition, boolean removeChildrenIndex) {
		String path = definition.getPath();
		definitionsByPath.remove(path);
		if ( removeChildrenIndex && definition instanceof EntityDefinition ) {
			((EntityDefinition) definition).traverse(new NodeDefinitionVisitor() {
				@Override
				public void visit(NodeDefinition defn) {
					String path = defn.getPath();
					definitionsByPath.remove(path);
				}
			});
		}
	}

	void indexById(NodeDefinition definition) {
		Integer id = definition.getId();
		if ( id != null ) {
			definitionsById.put(id, definition);
		}
	}

	public Set<String> getDefinedPaths() {
		return Collections.unmodifiableSet(definitionsByPath.keySet());
	}

	public Collection<NodeDefinition> getAllDefinitions() {
		return Collections.unmodifiableCollection(definitionsByPath.values());
	}
	
	public List<EntityDefinition> getRootEntityDefinitions() {
		return CollectionUtil.unmodifiableList(rootEntityDefinitions);
	}

//	public void addRootEntityDefinition(EntityDefinition defn) {
//		defn.setId(nextNodeDefinitionId());
//		if ( rootEntityDefinitions == null) {
//			rootEntityDefinitions = new ArrayList<EntityDefinition>();
//		}
//		rootEntityDefinitions.add(defn);
//		defn.setSchema(this);
//		indexById(defn);
//		indexByPath(defn);
//	}
	
	protected int nextNodeDefinitionId() {
		if ( lastDefinitionId == 0 ) {
			lastDefinitionId = calculateLastUsedDefinitionId();
		}
		return lastDefinitionId++;
	}
	
	protected int calculateLastUsedDefinitionId() {
		int result = 0;
		Stack<NodeDefinition> stack = new Stack<NodeDefinition>();
		List<EntityDefinition> rootEntityDefinitions = getRootEntityDefinitions();
		stack.addAll(rootEntityDefinitions);
		while ( ! stack.isEmpty() ) {
			NodeDefinition nodeDefn = stack.pop();
			result = Math.max(result, nodeDefn.getId());
			if ( nodeDefn instanceof EntityDefinition ) {
				List<NodeDefinition> childDefinitions = ((EntityDefinition) nodeDefn).getChildDefinitions();
				stack.addAll(childDefinitions);
			}
		}
		return result;
	}

	public void removeRootEntityDefinition(String name) {
		EntityDefinition defn = getRootEntityDefinition(name);
		removeRootEntityDefinition(defn);
	}

	public void removeRootEntityDefinition(int id) {
		EntityDefinition defn = getRootEntityDefinition(id);
		removeRootEntityDefinition(defn);
	}
	
	protected void removeRootEntityDefinition(EntityDefinition defn) {
		rootEntityDefinitions.remove(defn);
		reindexDefinitions();
	}
	
	public EntityDefinition getRootEntityDefinition(String name) {
		return (EntityDefinition) getByPath("/"+name);
	}
	
	public EntityDefinition getRootEntityDefinition(int id) {
		for (EntityDefinition node : rootEntityDefinitions) {
			if ( node.getId() == id ) {
				return node;
			}
		}
		throw new IllegalArgumentException("Root entity definition with id " + id + " not found");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rootEntityDefinitions == null) ? 0 : rootEntityDefinitions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schema other = (Schema) obj;
		if (rootEntityDefinitions == null) {
			if (other.rootEntityDefinitions != null)
				return false;
		} else if (!rootEntityDefinitions.equals(other.rootEntityDefinitions))
			return false;
		return true;
	}

}
