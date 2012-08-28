package org.openforis.idm.transform;

import java.util.Iterator;
import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.path.Axis;

/**
 * @author G. Miceli
 */
public class Transformation {
	
	private Axis rowAxis;
	private NodeColumnProvider provider;
	
	public Transformation(Axis rowAxis, NodeColumnProvider provider) throws InvalidExpressionException {
		this.rowAxis = rowAxis;
		this.provider = provider;
	}

	public Axis getRowAxis() {
		return rowAxis;
	}
	
	public static Transformation createDefaultTransformation(Schema schema, Axis rowAxis) throws InvalidExpressionException {
		return createDefaultTransformation(schema, rowAxis, null);
	}
	
	public static Transformation createDefaultTransformation(Schema schema, Axis rowAxis, ChildExpansionFilter childExpansionFilter) throws InvalidExpressionException {
		NodeDefinition defn = rowAxis.evaluate(schema);
		if ( defn instanceof EntityDefinition ) {
			NodeColumnProvider ncp = new EntityColumnProvider((EntityDefinition) defn, null, childExpansionFilter);
			return new Transformation(rowAxis, ncp);
		} else {
			throw new UnsupportedOperationException("Attribute and Field row axes not yet supported");
		}
	}
	
	
	public NodeColumnProvider getRootColumnProvider() {
		return provider;
	}
	
	public Result transform(Record record) throws IllegalTransformationException {
		List<Node<?>> rowNodes = rowAxis.evaluate(record);
		return new Result(rowNodes);
	}
	
	public class Result implements Iterable<Row> {
		private List<Node<?>> rowNodes;
		private List<Column> columns;
		
		private Result(List<Node<?>> rowNodes) throws IllegalTransformationException {
			this.columns = provider.getColumns();
			this.rowNodes = rowNodes;
		}
		
		public List<Column> getColumns() {
			return columns;
		}
		
		@Override
		public Iterator<Row> iterator() {
			
			final Iterator<Node<?>> iterator = rowNodes.iterator();
			
			return new Iterator<Row>() {

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Row next() {
					try {
						Node<?> node = iterator.next();
						List<Cell> cells;
						cells = provider.getCells(node);
						return new Row(cells);
					} catch (IllegalTransformationException e) {
						throw new RuntimeException("Transformation exception should have already been thrown by prior call to getColumns()");
					}
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		
	}
}
