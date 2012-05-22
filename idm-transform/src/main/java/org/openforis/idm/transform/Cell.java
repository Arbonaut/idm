package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 */
public class Cell {
	private Object value;
	private List<Node<?>> sourceNodes;
	private Class<?> valueType;
	private Column column;

	public Cell(Object value, Class<?> valueType, Column column, Node<?>... sourceNodes) {
		this.value = value;
		this.valueType = valueType;
		this.column = column;
		this.sourceNodes = Arrays.asList(sourceNodes);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<Node<?>> getSourceNodes() {
		return CollectionUtil.unmodifiableList(sourceNodes);
	}

	public void setSourceNodes(List<Node<?>> sourceNodes) {
		this.sourceNodes = sourceNodes;
	}

	public void setSourceNode(Node<?> sourceNode) {
		sourceNodes = new ArrayList<Node<?>>(1);
		sourceNodes.add(sourceNode);
	}

	public Class<?> getValueType() {
		return valueType;
	}

	public Column getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
