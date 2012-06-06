package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;

public abstract class ExNodeColumnProvider<N extends Node<D>, D extends NodeDefinition> extends NodeColumnProvider {

	private D nodeDefinition;
	private Heading heading;

	public ExNodeColumnProvider(D nodeDefinition, Heading heading) {
		this.nodeDefinition = nodeDefinition;
		this.heading = heading;
	}
	/*
	@Override
	public List<Column> getColumns() {
		Heading mainHeading = new Heading(nodeDefinition.getName(), "", nodeDefinition, heading, this);  // TODO heading
		List<Column> columns;
		if ( nodeDefinition.isMultiple() ) {
			if ( expandMultiple ) {
				columns = expandMultipleNodeColumns(mainHeading);
			} else {
				columns = collapseMultipleNodeColumns(mainHeading);
			}
		} else {
			columns = getColumnsForNodeInstance(mainHeading);
		}
		return Collections.unmodifiableList(columns);
	}

	/*
	protected List<Column> expandMultipleNodeColumns(Heading mainHeading) {
		Integer maxCount = nodeDefinition.getMaxCount();
		if ( maxCount == null ) {
			throw new UnsupportedOperationException("Can't expand unbounded multiple nodes into columns");
		} 
		List<Column> columns; columns = new ArrayList<Column>();
		for (int i = 1; i <= maxCount; i++) {
			String s = String.valueOf(i+1);
			Heading idxHeading = new Heading(s, s, nodeDefinition, mainHeading, this);
			List<Column> cols = getColumnsForNodeInstance(idxHeading);
			columns.addAll(cols);
		}
		return columns;
	}

	protected abstract List<Column> collapseMultipleNodeColumns(Heading mainHeading);

	protected abstract List<Column> getColumnsForNodeInstance(Heading heading);

	@SuppressWarnings("unchecked")
	@Override
	public List<Cell<?>> getCells(Node<?> parent) {
		if ( ! (parent instanceof Entity) ) {
			throw new IllegalArgumentException("Expected "+Entity.class+" but got "+parent.getClass());
		}
		Entity parentEntity = (Entity) parent;  
		List<Cell<?>> cells;
		String nodeName = nodeDefinition.getName();
		List<N> childNodes = (List<N>) parentEntity.getAll(nodeName);
		if ( nodeDefinition.isMultiple() ) {
			if ( expandMultiple  ) {
				cells = expandMultipleNodeCells(childNodes);
			} else {
				cells = collapseMultipleNodeCells(childNodes);
			}
 		} else {
 			if ( childNodes.isEmpty() ) {
 				cells = getEmptyCells();
 			} else {
 				N node = childNodes.get(0);
				cells = getCellsForNodeInstance(node);
 			}
 		}

		return Collections.unmodifiableList(cells);
	}

	@SuppressWarnings("unchecked")
	protected List<Cell<?>> expandMultipleNodeCells(List<N> childNodes) {
		Integer maxCount = nodeDefinition.getMaxCount();
		List<Cell<?>> cells = new ArrayList<Cell<?>>();
		for (int i = 0; i < maxCount; i++) {
			N node;
			if ( i < childNodes.size() ) {
				node = (N) childNodes.get(i);
			} else {
				node = null;
			}
			List<Cell<?>> c = getCellsForSingleNode(node);
			cells.addAll(c);
		}
		return cells;
	}

	protected abstract List<Cell<?>> collapseMultipleNodeCells(Entity parentEntity);

	protected abstract List<Cell<?>> getCellsForSingleNode(N node);
	
	protected abstract List<Cell<?>> getCellsForNodeInstance(N node);
*/
	public D getNodeDefinition() {
		return nodeDefinition;
	}
	
	public boolean isExpandMultiple() {
		return expandMultiple;
	}

	public void setExpandMultiple(boolean expandMultiple) {
		this.expandMultiple = expandMultiple;
	}

	public Heading getHeading() {
		return heading;
	}

	public void setHeading(Heading parentHeading) {
		this.heading = parentHeading;
	}
}
