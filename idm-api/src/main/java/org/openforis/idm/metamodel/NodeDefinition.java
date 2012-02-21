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

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

import org.openforis.idm.metamodel.expression.SchemaExpression;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class NodeDefinition extends Versionable implements Annotatable, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private Integer id;
	
	@XmlTransient
	@XmlParent
	private EntityDefinition parentDefinition;
	
	@XmlTransient
	@XmlParent
	@XmlInherited("schema")
	private Schema schema;
	
	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "relevant")
	private String relevantExpression;

	@XmlAttribute(name = "required")
	private Boolean required;
	
	@XmlAttribute(name = "requiredIf")
	private String requiredExpression;

	@XmlAttribute(name = "multiple")
	private Boolean multiple;

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
//		if ( path.startsWith("/") ) {
//			return getSchema().getByPath(path);
//		} else {
		SchemaExpression expression = new SchemaExpression(path);
		Object object = expression.evaluate(this);
		if (object instanceof NodeDefinition) {
			return (NodeDefinition) object;
		}
		return null;
//		}
	}

	public String getName() {
		return this.name;
	}

	public String getRelevantExpression() {
		return this.relevantExpression;
	}

	public boolean isRequired() {
		if (required == null) {
			return minCount != null && minCount >= 1;
		} else {
			return required;
		}
	}
	
	public String getRequiredExpression() {
		return requiredExpression;
	}

	public boolean isMultiple() {
		if ( multiple == null ) {
			return (minCount != null && minCount > 1) || (maxCount != null && maxCount > 1); 
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

	public EntityDefinition getParentDefinition() {
		return this.parentDefinition;
	}

	protected void setParentDefinition(EntityDefinition parentDefinition) {
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
}
