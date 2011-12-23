/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.openforis.idm.metamodel.impl.jxpath.MetaModelExpression;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class SchemaObjectDefinition extends VersionableModelDefinition implements Annotatable {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "relevant")
	private String relevantExpression;

	@XmlAttribute(name = "required")
	private String requiredExpression;

	@XmlAttribute(name = "multiple")
	private boolean multiple;

	@XmlAttribute(name = "minCount")
	private Integer minCount;

	@XmlAttribute(name = "maxCount")
	private Integer maxCount;

	@XmlElement(name = "label", type = Label.class)
	private List<Label> labels;

	@XmlElement(name = "prompt", type = Prompt.class)
	private List<Prompt> prompts;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlTransient
	private List<ModelAnnotation> annotations;
	
	@XmlTransient
	private EntityDefinition parentDefinition;
	
	public SchemaObjectDefinition get(String path) {
		MetaModelExpression expression = new MetaModelExpression(path);
		Object object = expression.evaluate(this);
		if (object instanceof SchemaObjectDefinition) {
			return (SchemaObjectDefinition) object;
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public String getRelevantExpression() {
		return this.relevantExpression;
	}

	public String getRequiredExpression() {
		return requiredExpression;
	}

	public boolean isMultiple() {
		return this.multiple;
	}

	public Integer getMinCount() {
		return minCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public List<Label> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<Label> getLabels(Label.Type type) {
		List<Label> list = new ArrayList<Label>();
		if (this.labels != null) {
			for (Label label : this.labels) {
				if (label.getType().equals(type)) {
					list.add(label);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}

	public List<Prompt> getPrompts() {
		return Collections.unmodifiableList(this.prompts);
	}

	public List<Prompt> getPrompts(Prompt.Type type) {
		List<Prompt> list = new ArrayList<Prompt>();
		if (this.prompts != null) {
			for (Prompt prompt : this.prompts) {
				if (prompt.getType().equals(type)) {
					list.add(prompt);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}
	
	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}
	
	public List<ModelAnnotation> getAnnotations() {
		return Collections.unmodifiableList(this.annotations);
	}

	/**
	 * Convenience method to access schema directly
	 */
	public Schema getSchema() {
		return getSurvey()==null ? null : getSurvey().getSchema();
	}

	public EntityDefinition getParentDefinition() {
		return this.parentDefinition;
	}

	protected void setParentDefinition(EntityDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
	}
	
	@Override
	public String toString() {
		if ( getName() == null ) {
			return "Unnamed " +getClass().getName(); 
		} else {
			return getClass().getName() + getName();
		}
	}
	
	@Override
	protected void beforeUnmarshal(Object parent) {
		super.beforeUnmarshal(parent);
		if ( parent instanceof EntityDefinition ) {
			this.parentDefinition = (EntityDefinition) parent;
		}
	}
}
