/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
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
import org.openforis.idm.metamodel.expression.SchemaPathExpression;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NodePathPointer;
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
	@XmlParent
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
	private List<NodeLabel> labels;

	@XmlElement(name = "prompt", type = Prompt.class)
	private List<Prompt> prompts;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

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

	public NodeDefinition getDefinitionByRelativePath(String path) {
		SchemaPathExpression expression = new SchemaPathExpression(path);
		Object object = expression.evaluate(this);
		if (object instanceof NodeDefinition) {
			return (NodeDefinition) object;
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
		return CollectionUtil.unmodifiableList(labels);
	}

	public List<NodeLabel> getLabels(NodeLabel.Type type) {
		List<NodeLabel> list = new ArrayList<NodeLabel>();
		if (labels != null) {
			for (NodeLabel label : labels) {
				if (label.getType().equals(type)) {
					list.add(label);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}
	
	public String getLabel(NodeLabel.Type type, String language) {
		NodeLabel label = getNodeLabel(type, language);
		if ( label != null ) {
			return label.getText();
		} else {
			return null;
		}
	}
	
	public void setLabel(NodeLabel.Type type, String language, String text) {
		if ( labels == null ) {
			labels = new ArrayList<NodeLabel>();
		}
		NodeLabel oldLabel = getNodeLabel(type, language);
		if ( oldLabel == null ) {
			NodeLabel newLabel = new NodeLabel(type, language, text);
			addLabel(newLabel);
		} else {
			oldLabel.setText(text);
		}
	}

	protected NodeLabel getNodeLabel(NodeLabel.Type type, String language) {
		if (labels != null ) {
			for (NodeLabel label : labels) {
				String labelLang = label.getLanguage();
				if ( label.getType()== type && ( language == null && labelLang == null ||
						language != null && language.equals(labelLang) ) ) {
					return label;
				}
			}
		}
		return null;
	}

	public void addLabel(NodeLabel label) {
		if (labels == null) {
			labels = new ArrayList<NodeLabel>();
		}
		labels.add(label);
	}
	
	public List<Prompt> getPrompts() {
		return CollectionUtil.unmodifiableList(prompts);
	}

	public List<Prompt> getPrompts(Prompt.Type type) {
		List<Prompt> list = new ArrayList<Prompt>();
		if (prompts != null) {
			for (Prompt prompt : prompts) {
				if (prompt.getType().equals(type)) {
					list.add(prompt);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}
	
	protected Prompt getPromptInstance(Prompt.Type type, String languageCode) {
		if (prompts != null) {
			for (Prompt prompt : prompts) {
				if (prompt.getType().equals(type)) {
					String promptLang = prompt.getLanguage();
					if ( languageCode == null && promptLang == null ||
						languageCode != null && languageCode.equals(promptLang) ) {
						return prompt;
					}
				}
			}
		}
		return null;
	}
	
	public String getPrompt(Prompt.Type type, String languageCode) {
		Prompt prompt = getPromptInstance(type, languageCode);
		return prompt != null ? prompt.getText(): null;
	}
	
	public void setPrompt(Prompt.Type type, String languageCode, String text) {
		Prompt oldPrompt = getPromptInstance(type, languageCode);
		if ( StringUtils.isNotBlank(text)) {
			Prompt newPrompt = new Prompt(type, languageCode, text);
			if ( oldPrompt != null ) {
				int index = prompts.indexOf(oldPrompt);
				prompts.set(index, newPrompt);
			} else {
				if ( prompts == null ) {
					prompts = new ArrayList<Prompt>();
				}
				prompts.add(newPrompt);
			}
		} else if ( oldPrompt != null ) {
			prompts.remove(oldPrompt);
		}
	}

	public void addPrompt(Prompt prompt) {
		if (prompts == null) {
			prompts = new ArrayList<Prompt>();
		}
		prompts.add(prompt);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return CollectionUtil.unmodifiableList(descriptions);
	}

	public String getDescription(String language) {
		if (descriptions != null ) {
			return LanguageSpecificText.getText(descriptions, language);
		} else {
			return null;
		}
	}
	
	public void setDescription(String language, String description) {
		if ( descriptions == null ) {
			descriptions = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(descriptions, language, description);
	}
	
	public void addDescription(LanguageSpecificText description) {
		if ( descriptions == null ) {
			descriptions = new ArrayList<LanguageSpecificText>();
		}
		descriptions.add(description);
	}

	public void removeDescription(String language) {
		LanguageSpecificText.remove(descriptions, language);
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
		checkLockState();
		Schema schema = getSchema();
		String oldName = this.name;
		boolean changed = ! StringUtils.equals(oldName, name);
		if ( schema != null && changed && oldName != null ) {
			schema.removeIndexByPath(this, true);
		}
		this.name = name;
		if ( schema != null && changed && name != null ) {
			schema.indexByPath(this, true);
		}
	}
	
	public void setRelevantExpression(String relevantExpression) {
		checkLockState();
		this.relevantExpression = relevantExpression;
	}

	public void setRequiredExpression(String requiredExpression) {
		checkLockState();
		this.requiredExpression = requiredExpression;
	}


	/**
	 * This property is meaningless for root entities
	 * @param multiple 
	 */
	public void setMultiple(boolean multiple) {
		checkLockState();
		this.multiple = multiple;
	}

	public void setMinCount(Integer minCount) {
		checkLockState();
		this.minCount = minCount;
	}

	public void setMaxCount(Integer maxCount) {
		checkLockState();
		this.maxCount = maxCount;
		if ( maxCount != null && maxCount > 1 ) {
			this.multiple = true;
		}
	}

	/**
	 * @throws IllegalStateException when the survey is already 
	 * associated with one or more records or nodes (i.e. locked)
	 */
	protected void checkLockState() {
		// TODO remove??
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
