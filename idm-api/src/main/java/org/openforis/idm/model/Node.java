/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.RelevanceExpression;
import org.openforis.idm.model.expression.RequiredExpression;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class Node<D extends NodeDefinition> {

	private Integer id;
	private Integer internalId;
	
	private D definition;

	private Record record;
	private Entity parent;

	public Node(D definition) {
		if ( definition == null ) {
			throw new NullPointerException("definition required");
		}
		this.definition = definition;
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

	public abstract ValidationResults validate();

	public boolean isRelevant() {
		String expr = getDefinition().getRequiredExpression();
		if (StringUtils.isNotBlank(expr)) {
			try {
				RelevanceExpression relevanceExpr = getExpressionFactory().createRelevanceExpression(expr);
				return relevanceExpr.evaluate(getParent(), this);
			} catch (InvalidExpressionException e) {
				throw new IdmInterpretationError("Unable to evaluate expression: " + expr, e);
			}
		}
		return true;
	}
	
	public boolean isRequired() {
		if (definition.isRequired()) {
			return true;
		} else {
			String expr = definition.getRequiredExpression();
			if (StringUtils.isNotBlank(expr)) {
				try {
					ExpressionFactory expressionFactory = getExpressionFactory();
					RequiredExpression requiredExpr = expressionFactory.createRequiredExpression(expr);
					return requiredExpr.evaluate(getParent(), this);
				} catch (InvalidExpressionException e) {
					throw new IdmInterpretationError("Error evaluating required", e);
				}
			}
			return false;
		}
	}

	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		write(sw,0);
		return sw.toString();
	}

	protected ExpressionFactory getExpressionFactory() {
		RecordContext context = getRecord().getContext();
		return context.getExpressionFactory();
	}

	public String getPath() {
		StringBuilder sb = new StringBuilder();
		getPath(sb);
		return sb.toString();
	}
	
	void getPath(StringBuilder sb) {
		if ( parent!=null ) {
			parent.getPath(sb);
		}
		String name = getName();
		int idx = getIndex();
		sb.append("/");
		sb.append(name);
		sb.append("[");
		sb.append(idx);
		sb.append("]");
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
}
