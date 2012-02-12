/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.geotools.IdmInterpretationError;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.model.expression.ModelPathExpression;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class UniquenessCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	@Override
	public boolean validate(Attribute<?, ?> node) {
		try {
			RecordContext recordContex = node.getRecord().getContext();
			ModelPathExpression pathExpression = recordContex.getExpressionFactory().createModelPathExpression(getExpression());
			List<Node<?>> list = pathExpression.iterate(node);
			boolean unique = true;
			if (list != null && list.size() > 0) {
				for (Node<?> object : list) {
					if (object instanceof Attribute) {
						Object value = ((Attribute<?, ?>) object).getValue();
						if (value.equals(node.getValue())) {
							unique = false;
							break;
						}
					}
				}
			}
			return unique;
		} catch (InvalidPathException e) {
			throw new IdmInterpretationError("Error evaluating uniqueness check", e);
		}
	}

}
