/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rootEntityDefinitions" })
public class Schema extends ModelDefinition {

	@XmlElement(name = "entity", type = EntityDefinition.class)
	private List<EntityDefinition> rootEntityDefinitions;

	@XmlTransient
	private Survey survey;

	public SchemaObjectDefinition get(String path) {
		MetaModelExpression expression = new MetaModelExpression(path);
		Object object = expression.evaluate(this);
		if (object instanceof SchemaObjectDefinition) {
			return (SchemaObjectDefinition) object;
		}
		return null;
	}

	public List<EntityDefinition> getRootEntityDefinitions() {
		return Collections.unmodifiableList(this.rootEntityDefinitions);
	}

	public Survey getSurvey() {
		return survey;
	}

	protected void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@Override
	protected void beforeUnmarshal(Object parent) {
		if (parent instanceof Survey) {
			this.survey = (Survey) parent;
		}
	}
}
