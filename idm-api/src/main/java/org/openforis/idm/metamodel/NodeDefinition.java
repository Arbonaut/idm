/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;*/
import javax.xml.namespace.QName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Transient;
import org.simpleframework.xml.ElementMap;

import org.openforis.idm.metamodel.expression.SchemaPathExpression;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
@Transient
public abstract class NodeDefinition extends Versionable implements Annotatable, Serializable {
	private static final long serialVersionUID = 1L;
//	private static final transient Log LOG = LogFactory.getLog(NodeDefinition.class);

	@Transient
	@XmlParent
	private NodeDefinition parentDefinition;
	
	@Transient
	@XmlParent
	@XmlInherited("schema")
	private Schema schema;
	
	@Attribute(name = "id")
	private Integer id;
	
	@Attribute(name = "name")
	private String name;

	@Attribute(name = "relevant")
	private String relevantExpression;

	@Attribute(name = "required")
	private Boolean required;
	
	@Attribute(name = "requiredIf")
	private String requiredExpression;

	@Attribute(name = "multiple")
	private Boolean multiple;

	@Attribute(name = "minCount")
	private Integer minCount;

	@Attribute(name = "maxCount")
	private Integer maxCount;

	/*@XmlElement(name = "label", type = NodeLabel.class)
	private List<NodeLabel> labels;*/
	@ElementList(inline=true, entry="label", type=NodeLabel.class)
	private List<NodeLabel> labels;

	/*@XmlElement(name = "prompt", type = Prompt.class)
	private List<Prompt> prompts;*/
	@ElementList(inline=true, entry="prompt", type=Prompt.class)
	private List<Prompt> prompts;

	/*@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;*/
	@ElementList(inline=true, entry="description", type=LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@ElementMap(name="annotations",key="annotations", keyType=QName.class, 
				valueType=String.class, attribute=true, inline=false)
	private Map<QName,String> annotations;
	/*@XmlAnyAttribute
	private Map<QName,String> annotations;*/
	
	public abstract Node<?> createNode();
	
	public String getAnnotation(QName qname) {
		return annotations == null ? null : annotations.get(qname);
	}

	public Set<QName> getAnnotationNames() {
		return CollectionUtil.unmodifiableSet(annotations.keySet());
	}
	
	public Integer getId() {
		return id;
	}

	// TODO Encapsulate this better (e.g. using reflection or subclass)
	public void setId(Integer id) {
		this.id = id;
		if ( schema != null ) {
			schema.indexById(this);
		}
	}
	
	public Schema getSchema() {
		return schema;
	}

	protected void setSchema(Schema schema) {
		this.schema = schema;
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

	/*
	public boolean isRequired() {
		if (required == null) {
			return minCount != null && minCount >= 1;
		} else {
			return required;
		}
	}
	*/
	
	public String getRequiredExpression() {
		return requiredExpression;
	}

	public boolean isMultiple() {
		if ( multiple == null ) {
			if ( parentDefinition == null && schema != null ) {
				// Root entity is always multiple
				return true;
			} else {
				return (minCount != null && minCount > 1) || (maxCount != null && maxCount > 1);
			}
		} else {
			return multiple;
		}
	}

	public Integer getMinCount() {
		if (minCount == null) {
			return required == Boolean.TRUE ? 1 : null;
		} else {
			return minCount;
		}
	}

	public Integer getMaxCount() {
		if ( maxCount == null && !isMultiple() ) {
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
	
	public List<LanguageSpecificText> getDescriptions() {
		return CollectionUtil.unmodifiableList(descriptions);
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
		this.schema = parentDefinition.getSchema();
	}
	
	@Override
	public Survey getSurvey() {
		return schema == null ? null : schema.getSurvey();
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
		this.name = name;
	}
	
	public void setRelevantExpression(String relevantExpression) {
		checkLockState();
		this.relevantExpression = relevantExpression;
	}

	public void setRequired(Boolean required) {
		checkLockState();
		this.required = required;
	}

	public void setRequiredExpression(String requiredExpression) {
		checkLockState();
		this.requiredExpression = requiredExpression;
	}

	public void setMultiple(Boolean multiple) {
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
	}

	/**
	 * @throws IllegalStateException when the survey is already 
	 * associated with one or more records or nodes (i.e. locked)
	 */
	protected void checkLockState() {
		// TODO
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((maxCount == null) ? 0 : maxCount.hashCode());
		result = prime * result + ((minCount == null) ? 0 : minCount.hashCode());
		result = prime * result + ((multiple == null) ? 0 : multiple.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prompts == null) ? 0 : prompts.hashCode());
		result = prime * result + ((relevantExpression == null) ? 0 : relevantExpression.hashCode());
		result = prime * result + ((required == null) ? 0 : required.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (multiple == null) {
			if (other.multiple != null)
				return false;
		} else if (!multiple.equals(other.multiple))
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
		if (required == null) {
			if (other.required != null)
				return false;
		} else if (!required.equals(other.required))
			return false;
		if (requiredExpression == null) {
			if (other.requiredExpression != null)
				return false;
		} else if (!requiredExpression.equals(other.requiredExpression))
			return false;
		return true;
	}

}
