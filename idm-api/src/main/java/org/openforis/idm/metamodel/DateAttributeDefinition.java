/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class DateAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<Integer>("year", "y", "y", Integer.class, this),
			new FieldDefinition<Integer>("month", "m", "m", Integer.class, this),
			new FieldDefinition<Integer>("day", "d", "d", Integer.class, this)
	};
	
	
	protected DateAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	@Override
	public Node<?> createNode() {
		return new DateAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Date.parseDate(string);
		}
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return Date.class;
	}
}
