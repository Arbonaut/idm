/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */

public class TimeAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
			new FieldDefinition<Integer>("hour", "h", "h", Integer.class, this), 
			new FieldDefinition<Integer>("minute", "m", "m", Integer.class, this)
	};
	
	protected TimeAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	@Override
	public Node<?> createNode() {
		return new TimeAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Time createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Time.parseTime(string);
		}
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}
	
	@Override
	public Class<? extends Value> getValueType() {
		return Time.class;
	}
}
