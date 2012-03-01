/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
public abstract class AttributeDefinition extends NodeDefinition {
	
	private static final transient Log LOG = LogFactory.getLog(AttributeDefinition.class);
	private static final long serialVersionUID = 1L;

	@XmlElements({ 
			@XmlElement(name = "distance", type = DistanceCheck.class), 
			@XmlElement(name = "pattern", type = PatternCheck.class), 
			@XmlElement(name = "compare", type = ComparisonCheck.class),
			@XmlElement(name = "check", type = CustomCheck.class), 
			@XmlElement(name = "unique", type = UniquenessCheck.class) 
			})
	private List<Check> checks;

	@XmlElement(name = "default", type = AttributeDefault.class)
	private List<AttributeDefault> attributeDefaults;

	@XmlTransient
	private Set<String> checkDependencyPaths;

	public List<Check> getChecks() {
		return CollectionUtil.unmodifiableList(this.checks);
	}

	public List<AttributeDefault> getAttributeDefaults() {
		return CollectionUtil.unmodifiableList(this.attributeDefaults);
	}

	public abstract <V> V createValue(String string);

	public Set<String> getCheckDependencyPaths() {
		if (checkDependencyPaths == null) {
			checkDependencyPaths = createCheckDependencyPaths();
		}
		return checkDependencyPaths;
	}

	private Set<String> createCheckDependencyPaths() {
		Set<String> paths = new HashSet<String>();
		for (Check check : getChecks()) {
			
			List<String> referencedPath = getReferencedPath(check.getCondition());
			paths.addAll(referencedPath);
			if (check instanceof ComparisonCheck) {
				referencedPath = getReferencedPath(((ComparisonCheck) check).getEqualsExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((ComparisonCheck) check).getLessThanExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((ComparisonCheck) check).getLessThanOrEqualsExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((ComparisonCheck) check).getGreaterThanExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((ComparisonCheck) check).getGreaterThanOrEqualsExpression());
				paths.addAll(referencedPath);
			} else if (check instanceof CustomCheck) {
				referencedPath = getReferencedPath(((CustomCheck) check).getExpression());
				paths.addAll(referencedPath);
			} else if (check instanceof DistanceCheck) {
				referencedPath = getReferencedPath(((DistanceCheck) check).getDestinationPointExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((DistanceCheck) check).getMaxDistanceExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((DistanceCheck) check).getMinDistanceExpression());
				paths.addAll(referencedPath);
				
				referencedPath = getReferencedPath(((DistanceCheck) check).getSourcePointExpression());
				paths.addAll(referencedPath);
			} else if (check instanceof UniquenessCheck) {
				referencedPath = getReferencedPath(((UniquenessCheck) check).getExpression());
				paths.addAll(referencedPath);
			}
		}
		return paths;
	}

	public List<String> getReferencedPath(String expression) {
		List<String> paths = new ArrayList<String>();
		if (StringUtils.isNotBlank(expression)) {
			List<String> referencedPaths = getReferencedPaths(expression);
			for (String path : referencedPaths) {
				try {
					NodeDefinition dependantNodeDefn = getDependantNodeDefinition(path);

					String sourcePath = getPath();
					String destinationPath = dependantNodeDefn.getPath();
					String relativePath = getRelativePath(sourcePath, destinationPath);

					paths.add(relativePath);
				} catch (Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error("Unable to register dependency for node " + getPath() + " with expression " + path, e);
					}
				}
			}
		}
		return paths;
	}

}
