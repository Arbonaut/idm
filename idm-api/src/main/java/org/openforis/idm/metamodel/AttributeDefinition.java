/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * 
 */
@XmlTransient
public abstract class AttributeDefinition extends NodeDefinition {
	
//	private static final transient Log LOG = LogFactory.getLog(AttributeDefinition.class);
	private static final long serialVersionUID = 1L;

	@XmlElements({ 
			@XmlElement(name = "distance", type = DistanceCheck.class), 
			@XmlElement(name = "pattern", type = PatternCheck.class), 
			@XmlElement(name = "compare", type = ComparisonCheck.class),
			@XmlElement(name = "check", type = CustomCheck.class), 
			@XmlElement(name = "unique", type = UniquenessCheck.class) 
			})
	private List<Check<?>> checks;

	@XmlElement(name = "default", type = AttributeDefault.class)
	private List<AttributeDefault> attributeDefaults;

//	@XmlTransient
//	private Set<String> checkDependencyPaths;

	public List<Check<?>> getChecks() {
		return CollectionUtil.unmodifiableList(this.checks);
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return CollectionUtil.unmodifiableList(this.attributeDefaults);
	}

	public abstract <V> V createValue(String string);

	public Set<NodePathPointer> getCheckDependencyPaths() {
		Survey survey = getSurvey();
		return survey.getCheckDependencies(this);
	}
	
	public abstract List<FieldDefinition> getFieldDefinitions();
	
	public FieldDefinition getFieldDefinition(String name) {
		List<FieldDefinition> defns = getFieldDefinitions();
		for (FieldDefinition fieldDefinition : defns) {
			if ( fieldDefinition.getName().equals(name) ) {
				return fieldDefinition;
			}
		}
		return null;
	}

	public abstract Class<?> getValueType();
//	private Set<String> createCheckDependencyPaths() {
//		Set<String> paths = new HashSet<String>();
//		for (Check<?> check : getChecks()) {
//			
//			addReferencedPath(check.getCondition(), paths);
//			if (check instanceof ComparisonCheck) {
//				addReferencedPath(((ComparisonCheck) check).getEqualsExpression(),paths);
//				addReferencedPath(((ComparisonCheck) check).getLessThanExpression(),paths);
//				addReferencedPath(((ComparisonCheck) check).getLessThanOrEqualsExpression(),paths);
//				addReferencedPath(((ComparisonCheck) check).getGreaterThanExpression(),paths);
//				addReferencedPath(((ComparisonCheck) check).getGreaterThanOrEqualsExpression(),paths);
//			} else if (check instanceof CustomCheck) {
//				addReferencedPath(((CustomCheck) check).getExpression(),paths);
//			} else if (check instanceof DistanceCheck) {
//				addReferencedPath(((DistanceCheck) check).getDestinationPointExpression(),paths);
//				addReferencedPath(((DistanceCheck) check).getMaxDistanceExpression(),paths);
//				addReferencedPath(((DistanceCheck) check).getMinDistanceExpression(),paths);
//				addReferencedPath(((DistanceCheck) check).getSourcePointExpression(),paths);
//			} else if (check instanceof UniquenessCheck) {
//				addReferencedPath(((UniquenessCheck) check).getExpression(),paths);
//			}
//		}
//		return paths;
//	}

//	public void addReferencedPath(String expression, Set<String> set) {
//		if (StringUtils.isNotBlank(expression)) {
//			List<String> referencedPaths = getReferencedPaths(expression);
//			for (String path : referencedPaths) {
//				try {
//					NodeDefinition dependantNodeDefn = getDependantNodeDefinition(path);
//
//					String sourcePath = getPath();
//					String destinationPath = dependantNodeDefn.getPath();
//					String relativePath = getRelativePath(sourcePath, destinationPath);
//					
//					if (StringUtils.isNotBlank(relativePath)) {
//						set.add(relativePath);
//					}
//				} catch (Exception e) {
//					if (LOG.isErrorEnabled()) {
//						LOG.error("Unable to register dependency for node " + getPath() + " with expression " + path, e);
//					}
//				}
//			}
//		}
//	}

}
