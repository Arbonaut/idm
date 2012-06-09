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

import org.openforis.idm.metamodel.expression.SchemaPathExpression;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class NodeDefinition extends Versionable implements Annotatable, Serializable {
	private static final long serialVersionUID = 1L;
//	private static final transient Log LOG = LogFactory.getLog(NodeDefinition.class);

	@XmlTransient
	private Integer id;
	
	@XmlTransient
	@XmlParent
	private NodeDefinition parentDefinition;
	
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
	
	/**
	 * For each NodeDefiniton X with relevance attr defined:
	 *    1. relative paths of parent of dependent node 
	 *    2. name or child def of child node   
	 *    (see {@link NodePathPointer}
	 */
//	@XmlTransient
//	private Set<NodePathPointer> relevantExpressionDependencies;

	/**
	 * For each NodeDefiniton X with requiredExpression defined:
	 *    1. relative paths of parent of dependent node 
	 *    2. name or child def of child node   
	 *    (see {@link NodePathPointer}
	 */
//	@XmlTransient
//	private Set<NodePathPointer> requiredExpressionDependencies;

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

	
	//	public Set<NodePathPointer> getDependencies(String expression) {
//		Set<NodePathPointer> nodePointers = new HashSet<NodePathPointer>();
//		if (StringUtils.isNotBlank(expression)) {
//			List<String> referencedPaths = getReferencedPaths(expression);
//			for (String path : referencedPaths) {
//				try {
//					NodeDefinition dependantNodeDefn = getDependantNodeDefinition(path);
//					EntityDefinition parentDependantDefn = dependantNodeDefn.getParentDefinition();
//
//					String sourcePath = getPath();
//					String destinationPath = parentDependantDefn.getPath();
//					String relativePath = getRelativePath(sourcePath, destinationPath);
//
//					NodePathPointer nodePointer = new NodePathPointer(relativePath, dependantNodeDefn.getName());
//					nodePointers.add(nodePointer);
//				} catch (Exception e) {
//					if (LOG.isErrorEnabled()) {
//						LOG.error("Unable to register dependency for node " + getPath() + " with expression " + path, e);
//					}
//				}
//			}
//		}
//		return nodePointers;
//	}

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

//	protected List<String> getReferencedPaths(String expression) {
//		if (StringUtils.isBlank(expression)) {
//			return Collections.emptyList();
//		} else {
//			try {
//				Survey survey = getSurvey();
//				SurveyContext surveyContext = survey.getContext();
//				ExpressionFactory expressionFactory = surveyContext.getExpressionFactory();
//				ModelPathExpression pathExpression = expressionFactory.createModelPathExpression(expression);
//				return pathExpression.getReferencedPaths();
//			} catch (InvalidExpressionException e) {
//				if (LOG.isErrorEnabled()) {
//					LOG.error("Invalid expression " + expression, e);
//				}
//				return Collections.emptyList();
//			}
//		}
//	}

//	protected String getRelativePath(String xpathSource, String xpathDestination) {
//		String path = "";
//		String[] sources = xpathSource.split("\\/");
//		String[] dests = xpathDestination.split("\\/");
//		int i = 0;
//		for (; i < sources.length; i++) {
//			if(dests.length == i){
//				break;
//			}
//			String src = sources[i];
//			String dest = dests[i];
//			if (dest.equals(src)) {
//				continue;
//			} else {
//				break;
//			}
//		}
//
//		for (int k = i; k < sources.length; k++) {
//			if (path != "")
//				path += "/";
//			path += "parent()";
//		}
//
//		for (int k = i; k < dests.length; k++) {
//			if (path != "")
//				path += "/";
//			path += dests[k];
//		}
//		return path;
//	}
//
//	protected NodeDefinition getDependantNodeDefinition(String path) {
//		String normalizedPath = path.replaceAll("\\$this/", "");
//		SchemaPathExpression schemaPathExpression = new SchemaPathExpression(normalizedPath);
//		EntityDefinition parentDefn = getParentDefinition();
//		NodeDefinition dependantNodeDefn = schemaPathExpression.evaluate(parentDefn);
//		return dependantNodeDefn;
//	}
	
}
