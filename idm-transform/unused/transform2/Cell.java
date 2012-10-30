package org.openforis.idm.transform2;

import java.util.Arrays;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 */
public class Cell {
	private Column column;
	private List<Node<?>> nodes;

	protected Cell(Column column, Node<?>... nodes) {
		this(column, Arrays.asList(nodes));
	}

	public Cell(Column column, List<Node<?>> nodes) {
		this.column = column;
		this.nodes = CollectionUtil.unmodifiableList(nodes);
	}

	public Column getColumn() {
		return column;
	}
	
	public List<Node<?>> getNodes() {
		return nodes;
	}
	
	@Override
	public String toString() {
		return String.valueOf(nodes);
	}
}
