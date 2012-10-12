/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.path.InvalidPathException;
import org.openforis.idm.path.Path;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class NodeDefinition extends VersionableSurveyObject implements Annotatable {
	private static final long serialVersionUID = 1L;
//	private static final transient Log LOG = LogFactory.getLog(NodeDefinition.class);

	@XmlTransient
	private NodeDefinition parentDefinition;
	
	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "relevant")
	private String relevantExpression;

	@XmlAttribute(name = "requiredIf")
	private String requiredExpression;

	@XmlAttribute(name = "multiple")
	private boolean multiple;

	@XmlAttribute(name = "minCount")
	private Integer minCount;

	@XmlAttribute(name = "maxCount")
	private Integer maxCount;

	@XmlElement(name = "label", type = NodeLabel.class)
	private NodeLabelMap labels;

	@XmlElement(name = "prompt", type = Prompt.class)
	private PromptMap prompts;

	@XmlElement(name = "description", type = LanguageSpecificTextMap.class)
	private LanguageSpecificTextMap descriptions;

	@XmlAnyAttribute
	private Map<QName,String> annotations;
	
	NodeDefinition(Survey survey, int id) {
		super(survey, id);
	}

	public abstract Node<?> createNode();
	
	public String getAnnotation(QName qname) {
		return annotations == null ? null : annotations.get(qname);
	}

	public void setAnnotation(QName qname, String value) {
		if ( annotations == null ) {
			annotations = new HashMap<QName, String>();
		}
		if (StringUtils.isNotBlank(value)) {
			annotations.put(qname, value);
		} else {
			annotations.remove(qname);
		}
	}
	
	public Set<QName> getAnnotationNames() {
		return CollectionUtil.unmodifiableSet(annotations.keySet());
	}

	public NodeDefinition getDefinitionByPath(String path) throws InvalidPathException {
		Path p = Path.parsePath(path);
		return p.evaluate(this);
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

	/**
	 * This property is meaningless for root entities
	 * @return 
	 */
	public boolean isMultiple() {
		return multiple;
	}

	public Integer getMinCount() {
		return minCount;
	}

	public Integer getMaxCount() {
		if ( !multiple ) {
			return 1;
		} else {
			return maxCount;
		}
	}

	public List<NodeLabel> getLabels() {
		if ( this.labels == null ) {
			return Collections.emptyList();
		} else {
			return this.labels.getAll();
		}
	}
	
	public String getLabel(NodeLabel.Type type, String language) {
		return labels == null ? null: labels.getText(type, language);
	}
	
	public void setLabel(NodeLabel.Type type, String language, String text) {
		if ( labels == null ) {
			labels = new NodeLabelMap();
		}
		labels.setText(type, language, text);
	}

	public void addLabel(NodeLabel label) {
		if ( labels == null ) {
			labels = new NodeLabelMap();
		}
		labels.add(label);
	}

	public void removeLabel(NodeLabel.Type type, String language) {
		if (labels != null ) {
			labels.remove(type, language);
		}
	}

	public List<Prompt> getPrompts() {
		if ( this.prompts == null ) {
			return Collections.emptyList();
		} else {
			return this.prompts.getAll();
		}
	}
	
	public String getPrompt(Prompt.Type type, String language) {
		return prompts == null ? null: prompts.getText(type, language);
	}
	
	public void setPrompt(Prompt.Type type, String language, String text) {
		if ( prompts == null ) {
			prompts = new PromptMap();
		}
		prompts.setText(type, language, text);
	}

	public void addPrompt(Prompt prompt) {
		if ( prompts == null ) {
			prompts = new PromptMap();
		}
		prompts.add(prompt);
	}

	public void removePrompt(Prompt.Type type, String language) {
		if (prompts != null ) {
			prompts.remove(type, language);
		}
	}
	
	public List<LanguageSpecificText> getDescriptions() {
		if ( this.descriptions == null ) {
			return Collections.emptyList();
		} else {
			return this.descriptions.getAll();
		}
	}

	public String getDescription(String language) {
		return descriptions == null ? null: descriptions.getText(language);
	}
	
	public void setDescription(String language, String description) {
		if ( descriptions == null ) {
			descriptions = new LanguageSpecificTextMap();
		}
		descriptions.setText(language, description);
	}
	
	public void addDescription(LanguageSpecificText description) {
		if ( descriptions == null ) {
			descriptions = new LanguageSpecificTextMap();
		}
		descriptions.add(description);
	}

	public void removeDescription(String language) {
		descriptions.remove(language);
	}
	
	public String getPath() {
		NodeDefinition defn = this;
		StringBuilder sb = new StringBuilder(64);
		while (defn!=null) {
			sb.insert(0, defn.getName());
			sb.insert(0, "/");
			defn = defn.getParentDefinition();
		} 
		return sb.toString();
	}

	public NodeDefinition getParentDefinition() {
		return this.parentDefinition;
	}
	
	protected void setParentDefinition(NodeDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
	}
	
	public EntityDefinition getRootEntity() {
		NodeDefinition ptr = this;
		while ( ptr.getParentDefinition() != null ) {
			ptr = ptr.getParentDefinition();
		}
		return (EntityDefinition) ptr;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Set<NodePathPointer> getRelevantExpressionDependencies() {
		Survey survey = getSurvey();
		return survey.getRelevanceDependencies(this);
	}

	public Set<NodePathPointer> getRequiredExpressionDependencies() {
		Survey survey = getSurvey();
		return survey.getRequiredDependencies(this);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setRelevantExpression(String relevantExpression) {
		this.relevantExpression = relevantExpression;
	}

	public void setRequiredExpression(String requiredExpression) {
		this.requiredExpression = requiredExpression;
	}


	/**
	 * This property is meaningless for root entities
	 * @param multiple 
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
		if ( maxCount != null && maxCount > 1 ) {
			this.multiple = true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + getId();
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((maxCount == null) ? 0 : maxCount.hashCode());
		result = prime * result + ((minCount == null) ? 0 : minCount.hashCode());
		result = prime * result + (multiple ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prompts == null) ? 0 : prompts.hashCode());
		result = prime * result + ((relevantExpression == null) ? 0 : relevantExpression.hashCode());
		result = prime * result + ((requiredExpression == null) ? 0 : requiredExpression.hashCode());
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
		NodeDefinition other = (NodeDefinition) obj;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (getId() != other.getId())
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (maxCount == null) {
			if (other.maxCount != null)
				return false;
		} else if (!maxCount.equals(other.maxCount))
			return false;
		if (minCount == null) {
			if (other.minCount != null)
				return false;
		} else if (!minCount.equals(other.minCount))
			return false;
		if (multiple!=other.multiple)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (prompts == null) {
			if (other.prompts != null)
				return false;
		} else if (!prompts.equals(other.prompts))
			return false;
		if (relevantExpression == null) {
			if (other.relevantExpression != null)
				return false;
		} else if (!relevantExpression.equals(other.relevantExpression))
			return false;
		if (requiredExpression == null) {
			if (other.requiredExpression != null)
				return false;
		} else if (!requiredExpression.equals(other.requiredExpression))
			return false;
		return true;
	}

}
