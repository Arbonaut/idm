/**
 * 
 */
package org.openforis.idm.model;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.ModelPathExpression;
import org.openforis.idm.model.expression.internal.MissingValueException;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class Node<D extends NodeDefinition> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	transient D definition;
	transient Record record;
	transient Integer id;
	transient Integer internalId;
	transient Entity parent;
	
	Integer definitionId;

	protected Node() {
	}
	
	public Node(D definition) {
		if ( definition == null ) {
			throw new NullPointerException("Definition required");
		}
		this.definition = definition;
		this.definitionId = definition.getId();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInternalId() {
		return internalId;
	}
	
	public D getDefinition() {
		return this.definition;
	}

	public String getName() {
		return getDefinition().getName();
	}

	public Record getRecord() {
		return record;
	}
	
	protected void setRecord(Record record) {
		this.record = record;
		this.internalId = record.nextId();
		record.put(this);
	}
	
	public Entity getParent() {
		return this.parent;
	}

	protected void setParent(Entity parent) {
		this.parent = parent;
		if ( parent.getRecord() != null ) {
			setRecord(parent.getRecord());
		}
	}

	protected abstract void write(StringWriter sw, int indent);

	public abstract boolean isEmpty();

//	public abstract ValidationResults validate();

	public List<Entity> getAncestors() {
		List<Entity> ancestors = new ArrayList<Entity>();
		Entity parent = getParent();
		while (parent != null) {
			ancestors.add(parent);
			parent = parent.getParent();
		}
		return ancestors;
	}
	
	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		write(sw,0);
		return sw.toString();
	}

	protected ExpressionFactory getExpressionFactory() {
		SurveyContext context = getRecord().getSurveyContext();
		return context.getExpressionFactory();
	}

	public String getPath() {
		StringBuilder sb = new StringBuilder();
		getPath(sb);
		return sb.toString();
	}
	
	
	protected void getPath(StringBuilder sb) {
		if ( parent!=null ) {
			parent.getPath(sb);
		}
		String name = getName();
		int idx = getIndex();
		sb.append("/");
		sb.append(name);
		sb.append("[");
		sb.append(idx+1);
		sb.append("]");
	}
	
	protected SurveyContext getRecordContext() {
		Record record = getRecord();
		SurveyContext context = record.getSurveyContext();
		return context;
	}

	public int getIndex() {
		if ( parent == null ) {
			return 0;
		} else {
			String name = getName();
			List<Node<?>> children = parent.getAll(name);
			for (int i=0; i<children.size(); i++) {
				if (children.get(i)==this) {
					return i;
				}
			}
			throw new IllegalStateException("Node parent relationship broken");
		}
	}
	
	public void clearDependencyStates(){
		Set<NodePointer> dependencies =  getRelevantDependencies();
		clearRelevantDependencies(dependencies);
		Set<NodePointer> requiredDependencies = getRequiredDependencies();
		dependencies.addAll(requiredDependencies);
		clearRequiredDependencies(dependencies);
	}
	
	private void clearRequiredDependencies(Set<NodePointer> nodePointers) {
		for (NodePointer nodePointer : nodePointers) {
			Entity entity = nodePointer.getEntity();
			entity.clearRequiredState(nodePointer.getChildName());
		}
	}
	
	private void clearRelevantDependencies(Set<NodePointer> nodePointers) {
		for (NodePointer nodePointer : nodePointers) {
			Entity entity = nodePointer.getEntity();
			entity.clearRelevanceState(nodePointer.getChildName());
		}
	}

	public Set<NodePointer> getRequiredDependencies() {
		Set<NodePointer> nodePointers = new HashSet<NodePointer>();
		Set<NodePathPointer> dependencies = getDefinition().getRequiredExpressionDependencies();
		for (NodePathPointer nodePathPointer : dependencies) {
			String entityPath = nodePathPointer.getEntityPath();
			String childName = nodePathPointer.getChildName();
			try {
				Entity entity = (Entity) evaluateModelPathExpression(this, entityPath);
				NodePointer nodePointer = new NodePointer(entity, childName);
				nodePointers.add(nodePointer);
			} catch (MissingValueException e) {
				continue;
			}
		}
		return nodePointers;
	}
	
	public Set<NodePointer> getRelevantDependencies(){
		Set<NodePointer> nodePointers = new HashSet<NodePointer>();
		Set<NodePathPointer> relevantExpressionDependencies = getDefinition().getRelevantExpressionDependencies();
		for (NodePathPointer nodePathPointer : relevantExpressionDependencies) {
			String entityPath = nodePathPointer.getEntityPath();
			String childName = nodePathPointer.getChildName();
			try {
				Entity entity = (Entity) evaluateModelPathExpression(getParent(), entityPath);
				NodePointer nodePointer = new NodePointer(entity, childName);
				addNodePointer(nodePointer, nodePointers);
			} catch (MissingValueException e) {
				continue;
			}
		}
		return nodePointers;
	}
	
	private void addNodePointer(NodePointer nodePointer, Set<NodePointer> nodePointers) {
		nodePointers.add(nodePointer);
		Entity entity = nodePointer.getEntity();
		List<Node<?>> children = entity.getAll(nodePointer.getChildName());
		for (Node<?> node : children) {
			if(node instanceof Entity){
				Entity childEntity = (Entity) node;
				EntityDefinition childEntityDefn = childEntity.getDefinition();
				List<NodeDefinition> childDefinitions = childEntityDefn.getChildDefinitions();
				for (NodeDefinition nodeDefn : childDefinitions) {
					String name = nodeDefn.getName();
					List<Node<? extends NodeDefinition>> list = childEntity.getAll(name);
					if(list.size()>0){
						NodePointer pointer = new NodePointer(childEntity, name);
						addNodePointer(pointer, nodePointers);
					}
				}
			}
		}
	}

	protected Node<?> evaluateModelPathExpression(Node<? extends NodeDefinition> node, String path) {
		Record record = node.getRecord();
		SurveyContext surveyContext = record.getSurveyContext();
		ExpressionFactory exprFactory = surveyContext.getExpressionFactory();
		try {
			ModelPathExpression pathExpression = exprFactory.createModelPathExpression(path);
			Node<?> resultNode = pathExpression.evaluate(node, node);
			return resultNode;
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Invalid path " + path, e);
		}

	}
	
	public Survey getSurvey() {
		return record == null ? null : record.getSurvey();
	}
	
	public Schema getSchema() {
		return getSurvey() == null ? null : getSurvey().getSchema();
	}

	public boolean isDetached(){
		return parent == null;
	}
	
	protected void detach() {
		parent = null;
		record = null;
	}
}
