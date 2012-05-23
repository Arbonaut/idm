package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.ModelPathExpression;

/**
 * @author G. Miceli
 */
public class XPathAxis implements Axis {
	
	private String pivotPath;
	private ModelPathExpression pivotExpression;

	public XPathAxis(String pivotPath) throws InvalidExpressionException {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		this.pivotPath = pivotPath;
		this.pivotExpression = expressionFactory.createModelPathExpression(pivotPath);
	}

	@Override
	public List<Node<?>> pivot(Node<?> node) {
		try {
			return pivotExpression.iterate(node, node);
		} catch (InvalidExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPivotPath() {
		return pivotPath;
	}
}
