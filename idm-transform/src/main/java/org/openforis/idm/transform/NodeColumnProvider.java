package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;
import org.openforis.idm.path.Path;

/**
 * @author G. Miceli
 */
public abstract class NodeColumnProvider implements ColumnProvider {
	private NodeDefinition nodeDefinition;
	private boolean expandMultiple;
	private boolean expandChildren;
	private List<NodeColumnProvider> childProviders;
	private NodeColumnProvider parentProvider;
	private ChildExpansionFilter childExpansionFilter;
	
	public NodeColumnProvider(NodeDefinition nodeDefinition, NodeColumnProvider parentProvider,
			ChildExpansionFilter childExpansionFilter) {
		this.nodeDefinition = nodeDefinition;
		this.parentProvider = parentProvider;
		this.childExpansionFilter = childExpansionFilter;
	}


	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}
	
	public boolean isExpandMultiple() {
		return expandMultiple;
	}
	
	public void setExpandMultiple(boolean expandMultiple) {
		this.expandMultiple = expandMultiple;
	}
	
	public boolean isExpandChildren() {
		return expandChildren;
	}
	
	public void setExpandChildren(boolean expandChildren) {
		this.expandChildren = expandChildren;
	}

	private ChildExpansionFilter getChildExpansionFilter() {
		if ( childExpansionFilter == null ) {
			if( parentProvider == null ) {
				return DefaultChildExpansionFilter.getInstance();
			} else {
				return parentProvider.getChildExpansionFilter();
			}
		} else {
			return childExpansionFilter;
		}
	}
	
	public List<NodeColumnProvider> getChildProviders() throws IllegalTransformationException {
		if ( childProviders == null ) {
			childProviders = new ArrayList<NodeColumnProvider>();
			createChildProviders(childProviders); 
		}
		return childProviders;
	}
	
	protected abstract void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException;
	
	@Override
	public List<Column> getColumns() throws IllegalTransformationException {
		return getColumns(null);
	};
	
	protected List<Column> getColumns(Path parentPath) throws IllegalTransformationException {
		boolean multiple = nodeDefinition.isMultiple();
		Integer maxCount = nodeDefinition.getMaxCount();
		String name = nodeDefinition.getName();
		List<Column> columns = new ArrayList<Column>();

		if ( expandMultiple && maxCount == null ) {
			throw new IllegalTransformationException("Cannot expand multiple nodes with unbounded maxCount");
		}
		
		if ( !multiple || expandMultiple ) {
			// For single nodes or expanded multiple, iterate over each instance
			for (int i = 0; i < maxCount; i++) {
				Path path = new Path(parentPath, name, i+1);
				if ( expandChildren ) {
					// Use child providers to expand children into columns
					List<NodeColumnProvider> providers = getChildProviders();
					for (NodeColumnProvider p : providers) {
						List<Column> cols = p.getColumns(path);
						columns.addAll(cols);
					}
				} else {
					// add single column for each child[i]
					Column col = new Column(this, path);
					columns.add(col);
				}
			}
		} else {
			// For collapsed multiple nodes, return all instances in a single column
			if ( expandChildren ) {
				throw new IllegalTransformationException("Cannot expand children without expanding multiple nodes");
			}
			Path path = new Path(parentPath, name);
			Column col = new Column(this, path);
			columns.add(col);
		}
		
		return Collections.unmodifiableList(columns);
	}
	
	@Override
	public List<Cell> getCells(Node<?> parentNode) throws IllegalTransformationException {
		List<Column> columns = getColumns();
		List<Cell> cells = new ArrayList<Cell>(columns.size());
		for (Column column : columns) {
			if ( !column.isExcluded() ) {
				Path path = column.getPath();
				List<Node<?>> nodes = path.evaluate(parentNode);
				Cell cell = new Cell(column, nodes);
				cells.add(cell);
			}
		}
		return Collections.unmodifiableList(cells);
	}

	public void expandAll() throws IllegalTransformationException {
		setExpandMultiple(true);
		setExpandChildren(true);
		for (NodeColumnProvider p : getChildProviders()) {
			NodeDefinition d = p.getNodeDefinition();
			if ( d.getMaxCount() != null ) {
				p.expandAll();
			}
		}
	}
	
	public void collapseAll() throws IllegalTransformationException {
		for (NodeColumnProvider p : getChildProviders()) {
			p.collapseAll();
		}
		setExpandMultiple(false);
		setExpandChildren(false);
	}

	public NodeColumnProvider getParentProvider() {
		return parentProvider;
	}
	
	public List<Cell> getEmptyCells(Path parentPath) throws IllegalTransformationException {
		List<Column> cols = getColumns(parentPath);
		List<Cell> cells = new ArrayList<Cell>(cols.size());
		for (Column col : cols) {
			Cell cell = new Cell(col);
			cells.add(cell);
		}
		return cells;
	}	

	/**
	 * Override this method to conditionally exclude children when expanding
	 * @param fieldDefinition
	 * @return
	 */
	protected boolean isIncluded(NodeDefinition nodeDefinition) {
		return getChildExpansionFilter().isIncluded(nodeDefinition);
	}
}