/**
 * 
 */
package org.openforis.idm.model.state;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.ModelPathExpression;

/**
 * @author M. Togna
 * 
 */
public class ModelDependencies {

	private ExpressionFactory expressionFactory;

	private Survey survey;
	private StateDependencyMap relevanceDependencies;
	private StateDependencyMap requiredDependencies;
	private StateDependencyMap defaultValueDependencies;
	private StateDependencyMap checkDependencies;

	public ModelDependencies(ExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
		reset();
	}

	public Set<Node<?>> getDependantNodes(Node<? extends NodeDefinition> node) {
		NodeDefinition defn = node.getDefinition();
		Set<String> paths = getDependantPaths(defn);
		return getNodes(node, paths);
	}

	public Set<Node<?>> getRelevanceDependantNodes(Node<? extends NodeDefinition> node) {
		NodeDefinition defn = node.getDefinition();
		Set<String> paths = getRelevanceDependantPaths(defn);
		return getNodes(node, paths);
	}
	
	public Set<Node<?>> getRequiredDependantNodes(Node<? extends NodeDefinition> node) {
		NodeDefinition defn = node.getDefinition();
		Set<String> paths = getRequiredDependantPaths(defn);
		return getNodes(node, paths);
	}
	
	private Set<Node<?>> getNodes(Node<? extends NodeDefinition> context, Set<String> paths) {
		Set<Node<?>> dependentNodes = new HashSet<Node<?>>();
		for (String path : paths) {
			Node<?> dependentNode = evaluateModelPathExpression(context, path);
			dependentNodes.add(dependentNode);
		}
		return dependentNodes;
	}

	public Set<String> getRelevanceDependantPaths(NodeDefinition definition) {
		String path = definition.getPath();
		return relevanceDependencies.getDependantPaths(path);
	}
	
	public Set<String> getRequiredDependantPaths(NodeDefinition definition) {
		String path = definition.getPath();
		return requiredDependencies.getDependantPaths(path);
	}
	
	public Set<String> getDependantPaths(NodeDefinition definition) {
		Set<String> set = new HashSet<String>();
		String path = definition.getPath();
		set.addAll(relevanceDependencies.getDependantPaths(path));
		set.addAll(requiredDependencies.getDependantPaths(path));
		set.addAll(defaultValueDependencies.getDependantPaths(path));
		set.addAll(checkDependencies.getDependantPaths(path));
		return set;
	}

	public void register(Survey survey) {
		this.survey = survey;
		reset();

		Schema schema = survey.getSchema();
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition entityDefinition : rootEntityDefinitions) {
			register(entityDefinition);
		}
	}

	private void register(EntityDefinition entityDefinition) {
		List<NodeDefinition> childDefinitions = entityDefinition.getChildDefinitions();
		for (NodeDefinition nodeDefinition : childDefinitions) {
			relevanceDependencies.register(nodeDefinition, nodeDefinition.getRelevantExpression());
			requiredDependencies.register(nodeDefinition, nodeDefinition.getRequiredExpression());

			if (nodeDefinition instanceof AttributeDefinition) {
				register((AttributeDefinition) nodeDefinition);
			} else {
				register((EntityDefinition) nodeDefinition);
			}
		}
	}

	private void register(AttributeDefinition attributeDefinition) {
		List<AttributeDefault> attributeDefaults = attributeDefinition.getAttributeDefaults();
		for (AttributeDefault attributeDefault : attributeDefaults) {
			defaultValueDependencies.register(attributeDefinition, attributeDefault.getCondition());
			defaultValueDependencies.register(attributeDefinition, attributeDefault.getExpression());
		}
		List<Check> checks = attributeDefinition.getChecks();
		for (Check check : checks) {
			checkDependencies.register(attributeDefinition, check.getCondition());
			if (check instanceof ComparisonCheck) {
				checkDependencies.register(attributeDefinition, ((ComparisonCheck) check).getEqualsExpression());
				checkDependencies.register(attributeDefinition, ((ComparisonCheck) check).getLessThanExpression());
				checkDependencies.register(attributeDefinition, ((ComparisonCheck) check).getLessThanOrEqualsExpression());
				checkDependencies.register(attributeDefinition, ((ComparisonCheck) check).getGreaterThanExpression());
				checkDependencies.register(attributeDefinition, ((ComparisonCheck) check).getGreaterThanOrEqualsExpression());
			} else if (check instanceof CustomCheck) {
				checkDependencies.register(attributeDefinition, ((CustomCheck) check).getExpression());
			} else if (check instanceof DistanceCheck) {
				checkDependencies.register(attributeDefinition, ((DistanceCheck) check).getDestinationPointExpression());
				checkDependencies.register(attributeDefinition, ((DistanceCheck) check).getMaxDistanceExpression());
				checkDependencies.register(attributeDefinition, ((DistanceCheck) check).getMinDistanceExpression());
				checkDependencies.register(attributeDefinition, ((DistanceCheck) check).getSourcePointExpression());
			} else if (check instanceof UniquenessCheck) {
				checkDependencies.register(attributeDefinition, ((UniquenessCheck) check).getExpression());
			}
		}
	}

	private Node<?> evaluateModelPathExpression(Node<? extends NodeDefinition> node, String path) {
		Record record = node.getRecord();
		RecordContext recordContext = record.getContext();
		ExpressionFactory exprFactory = recordContext.getExpressionFactory();
		try {
			ModelPathExpression pathExpression = exprFactory.createModelPathExpression(path);
			Node<?> resultNode = pathExpression.evaluate(node, node);
			return resultNode;
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Invalid path " + path, e);
		}

	}

	private void reset() {
		relevanceDependencies = new StateDependencyMap(expressionFactory);
		requiredDependencies = new StateDependencyMap(expressionFactory);
		defaultValueDependencies = new StateDependencyMap(expressionFactory);
		checkDependencies = new StateDependencyMap(expressionFactory);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		String name = survey.getName();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelDependencies other = (ModelDependencies) obj;
		if (survey.getName() == null) {
			if (other.survey.getName() != null)
				return false;
		} else if (!survey.getName().equals(other.survey.getName()))
			return false;
		return true;
	}

}
