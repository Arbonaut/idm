/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class NodeDefinition extends Versionable implements Annotatable {

	@XmlTransient
	private Integer id;
	
	@XmlTransient
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

	@XmlElement(name = "label", type = Label.class)
	private List<Label> labels;

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
	
	@XmlTransient
	private EntityDefinition parentDefinition;
	
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
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Label extends LanguageSpecificText {

		public enum Type {
			HEADING, INSTANCE, NUMBER;
		}

		@XmlAttribute(name = "type")
		@XmlJavaTypeAdapter(value = TypeAdapter.class)
		private Type type;

		protected Label() {
		}

		public Label(Type type, String language, String text) {
			super(language, text);
			this.type = type;
		}

		public Type getType() {
			return this.type;
		}

		private static class TypeAdapter extends XmlAdapter<String, Type> {
			@Override
			public Type unmarshal(String v) throws Exception {
				return v==null ? null : Type.valueOf(v.toUpperCase());
			}

			@Override
			public String marshal(Type v) throws Exception {
				return v==null ? null : v.toString().toLowerCase();
			}
		}
	}
}
