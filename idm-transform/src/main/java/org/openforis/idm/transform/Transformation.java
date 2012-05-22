package org.openforis.idm.transform;

import java.util.Iterator;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.AbsoluteModelPathExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author G. Miceli
 */
public class Transformation {
	private String axisPath;
	private ColumnProvider provider;
	private AbsoluteModelPathExpression pivotExpression;
	
	public Transformation(String axisPath, ColumnProvider provider) throws InvalidExpressionException {
		this.axisPath = axisPath;
		this.provider = provider;
		ExpressionFactory expressionFactory = new ExpressionFactory();
		this.pivotExpression = expressionFactory.createAbsoluteModelPathExpression(axisPath);
	}
	
	public String getAxisPath() {
		return axisPath;
	}
	
	public ColumnProvider getColumnProvider() {
		return provider;
	}
	
	public Result transform(Record record) throws InvalidExpressionException {
		List<Node<?>> rowNodes = pivotExpression.iterate(record);
		return new Result(rowNodes);
	}
	
	public class Result implements Iterable<Row> {
		private List<Node<?>> rowNodes;
		
		private Result(List<Node<?>> rowNodes) {
			this.rowNodes = rowNodes;
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
					Node<?> node = iterator.next();
					List<Cell> cells = provider.getCells(node);
					return new Row(cells);
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		
	}
}
