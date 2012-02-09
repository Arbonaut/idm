/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.model.expression.RelevanceExpression;
import org.openforis.idm.model.expression.RequiredExpression;
import org.openforis.idm.validation.ValidationResults;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class Node<D extends NodeDefinition> {

	private Integer id;
	
	private D definition;

	private Record record;
	private Entity parent;
//	private String path;
//	private String type;

	public Node(D definition) {
		if ( definition == null ) {
			throw new NullPointerException("Null definition");
		}
		this.definition = definition;
	}
	
	public Integer getId() {
		return id;
	}
	
	public D getDefinition() {
		return this.definition;
	}

	public String getName() {
		return this.getDefinition().getName();
	}

	public Record getRecord() {
		return record;
	}
	
	protected void setRecord(Record record) {
		this.record = record;
		this.id = record.nextId();
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

	public abstract ValidationResults validate();

	public boolean isRelevant() {
		String expr = getDefinition().getRequiredExpression();
		if (StringUtils.isNotBlank(expr)) {
			try {
				RelevanceExpression relevanceExpr = getExpressionFactory().createRelevanceExpression(expr);
				return relevanceExpr.evaluate(getParent());
			} catch (InvalidPathException e) {
				throw new RuntimeException("Unable to evaluate expression: " + expr, e);
			}
		}
		return Boolean.TRUE;
	}
	
	public boolean isRequired() {
		if (getDefinition().isRequired()) {
			return Boolean.TRUE;
		} else {
			String expr = getDefinition().getRequiredExpression();
			if (StringUtils.isNotBlank(expr)) {
				try {
					RequiredExpression requiredExpr = getExpressionFactory().createRequiredExpression(expr);
					return requiredExpr.evaluate(getParent());
				} catch (InvalidPathException e) {
					throw new RuntimeException("Unable to evaluate expression: " + expr, e);
				}
			}
			return Boolean.FALSE;
		}
	}

//	protected void notifyObservers() {
//		if ( record != null ) {
//			record.notifyObservers();
//		}
//	}
	
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

}
