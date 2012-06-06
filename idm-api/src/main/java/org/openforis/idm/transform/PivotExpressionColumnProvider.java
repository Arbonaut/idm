package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.ModelPathExpression;
import org.openforis.idm.model.expression.internal.MissingValueException;

/**
 * @author G. Miceli
 * @deprecated replaced with idm-transform api
 */
@Deprecated
public class PivotExpressionColumnProvider extends ColumnProviderChain {
	private ModelPathExpression expression;

	public PivotExpressionColumnProvider(String expression, ColumnProvider... providers) {
		this(expression, "", Arrays.asList(providers));
	}

	public PivotExpressionColumnProvider(String expression, String headingPrefix, ColumnProvider... providers) {
		this(expression, headingPrefix, Arrays.asList(providers));
	}
	
	public PivotExpressionColumnProvider(String expression, String headingPrefix, List<ColumnProvider> providers) {
		super(headingPrefix, providers);
		try {
			ExpressionFactory ef = new ExpressionFactory();
			this.expression = ef.createModelPathExpression(expression);
		} catch (InvalidExpressionException e) {
			throw new IllegalArgumentException();
		}
	}

	public List<String> extractValues(Node<?> n) {
		try {
			ArrayList<String> v = new ArrayList<String>();
			Node<?> axis = expression.evaluate(n, n);
			if ( axis == null ) {
				return emptyValues();
			} else {
				for (ColumnProvider p : getColumnProviders()) {
					v.addAll(p.extractValues(axis));
				}
				return v;
			}
		} catch (InvalidExpressionException e) {
			throw new IllegalArgumentException(e);
		} catch (MissingValueException e ) {
			return emptyValues();
		}
	}
}
