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

import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlParent;

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
	
	public String getAnnotation(QName qname) {
		return annotations == null ? null : annotations.get(qname);
	}

	public Set<QName> getAnnotationNames() {
		return annotations == null ? null : Collections.unmodifiableSet(annotations.keySet());
	}
//	protected void setAnnotationAttributes(Map<QName,String> attrMap) {
//		this.annotations = new ArrayList<ModelAnnotation>(attrMap.size()); 
//		for (Map.Entry<QName, String> entry : attrMap.entrySet()) {
//			String namespaceURI = entry.getKey().getNamespaceURI();
//			String name = entry.getKey().getLocalPart();
//			String value = entry.getValue();
//			ModelAnnotation ann = new ModelAnnotation(namespaceURI, name, value);
//			annotations.add(ann);
//		}
//	}
//	
//	protected Map<QName,String> getAnnotationAttributes() {
//		if ( annotations == null ) {
//			return null;
//		}
//		Map<QName,String> attrMap = new HashMap<QName, String>(annotations.size());
//		for (ModelAnnotation ann : annotations) {
//			QName qname = new QName(ann.getNamespace(), localPart)
//		}
//	}	
	
//	@XmlTransient
//	private List<ModelAnnotation> annotations;
	
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

	public String getRequiredExpression() {
		return requiredExpression;
	}

	public boolean isMultiple() {
		if ( maxCount != null && maxCount > 1 ) {
			return true;
		} else if ( multiple == null ) {
			return false;
		} else {
			return multiple;
		}
	}

	public Integer getMinCount() {
		return minCount;
	}

	public Integer getMaxCount() {
		if ( maxCount == null && !isMultiple() ) {
			return Integer.valueOf(1);
		} else {
			return maxCount;
		}
	}

	public List<NodeLabel> getLabels() {
		List<NodeLabel> list = this.labels != null ? this.labels : new ArrayList<NodeLabel>();
		return Collections.unmodifiableList(list);
	}

	public List<NodeLabel> getLabels(NodeLabel.Type type) {
		List<NodeLabel> list = new ArrayList<NodeLabel>();
		if (this.labels != null) {
			for (NodeLabel label : this.labels) {
				if (label.getType().equals(type)) {
					list.add(label);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}

	public List<Prompt> getPrompts() {
		List<Prompt> list = this.prompts != null ? this.prompts : new ArrayList<Prompt>();
		return Collections.unmodifiableList(list);
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
		List<LanguageSpecificText> list = this.descriptions != null ? this.descriptions : new ArrayList<LanguageSpecificText>();
		return Collections.unmodifiableList(list);
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
		return getClass().getSimpleName() + "("+getName()+")";
	}
}
