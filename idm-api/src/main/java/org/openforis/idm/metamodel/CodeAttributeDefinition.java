/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;
import org.openforis.idm.path.InvalidPathException;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CodeAttributeDefinition extends AttributeDefinition implements KeyAttributeDefinition  {

	private static final long serialVersionUID = 1L;
	
	private final FieldDefinition<?>[] FIELD_DEFINITIONS = {
		new FieldDefinition<String>("code", "c", null, String.class, this), 
		new FieldDefinition<String>("qualifier", "q", "other", String.class, this)
	};

	private boolean key;
	private boolean allowUnlisted;
	private String parentExpression;
	private CodeList list;
	private CodeAttributeDefinition parentCodeAttributeDefinition; 
	
	CodeAttributeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public CodeList getList() {
		return this.list;
	}

	public void setList(CodeList list) {
		if ( list == null ) {
			throw new IllegalArgumentException("Cannot add a null list");
		} else if ( list.getSurvey() == null || ! list.getSurvey().equals(this.getSurvey() )) {
			throw new IllegalArgumentException("Cannot add a list from a different survey");
		} else {
			this.list = list;
		}
	}
	
	public String getListName() {
		return list == null ? null : list.getName();
	}
	
	public void setListName(String name) {
		Survey survey = getSurvey();
		if ( survey == null ) {
			throw new DetachedNodeDefinitionException(CodeAttributeDefinition.class, Survey.class);
		}
		CodeList newList = survey.getCodeList(name);
		if ( newList == null ) {
			throw new IllegalArgumentException("Code list '"+name+"' not defined");
		}
		this.list = newList;
	}
	
	@Override
	public boolean isKey() {
		return key;
	}
	
	@Override
	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isAllowUnlisted() {
		return allowUnlisted;
	}
	
	public void setAllowUnlisted(Boolean allowUnlisted) {
		this.allowUnlisted = allowUnlisted;
	}
	
	public String getParentExpression() {
		return this.parentExpression;
	}
	
	public void setParentExpression(String parentExpression) {
		this.parentExpression = parentExpression;
	}

	public CodeAttributeDefinition getParentCodeAttributeDefinition() {
		if (StringUtils.isNotBlank(parentExpression) && parentCodeAttributeDefinition == null) {
			NodeDefinition parentDefinition = getParentDefinition();
			try {
				parentCodeAttributeDefinition = (CodeAttributeDefinition) parentDefinition.getDefinitionByPath(parentExpression);
			} catch (InvalidPathException e) {
				throw new IllegalStateException("Invalid parent paths should not be allowed");
			}
		}
		return parentCodeAttributeDefinition;
	}

	@Override
	public Node<?> createNode() {
		return new CodeAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Code createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return new Code(string);
		}
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		return Collections.unmodifiableList(Arrays.asList(FIELD_DEFINITIONS));
	}

	@Override
	public Class<? extends Value> getValueType() {
		return Code.class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (allowUnlisted ? 1231 : 1237);
		result = prime * result + (key ? 1231 : 1237);
		result = prime * result + ((parentExpression == null) ? 0 : parentExpression.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeAttributeDefinition other = (CodeAttributeDefinition) obj;
		if (allowUnlisted != other.allowUnlisted)
			return false;
		if (key != other.key)
			return false;
		if (parentExpression == null) {
			if (other.parentExpression != null)
				return false;
		} else if (!parentExpression.equals(other.parentExpression))
			return false;
		return true;
	}

	public int getCodeListLevel() {
		if ( list == null || list.getHierarchy().isEmpty() ) {
			return 0;
		} else {
			int level = 0;
			CodeAttributeDefinition ptr = getParentCodeAttributeDefinition();
			while ( ptr != null ) {
				level ++;
				ptr = ptr.getParentCodeAttributeDefinition();
			}
			return level;
		}
	}
}
