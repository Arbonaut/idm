/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;
import java.util.Set;

import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.model.expression.ExpressionFactory;

/*
Ax: = ancestors of x
Dx := descendants of x
Lx := relevanceDependencies U relevanceDependencies descendants 
Qx := requiredDependencies of x
Rx := Lx U Qx
Vx := x U checkDependencies



On update value of attribute x, field.setXXX clear state of dependent nodes:
1. Clear relevance states of all nodes in Lx
2. Clear required states of all nodes in Rx
3. [Clear minCount validation state of all nodes in Rx U Ax] <-- skip for now
4. Clear all value validation results of nodes Vx

Send client updated states:
1. For each node in Rx U Ax: (entityId, childName, relevant, required, minCountValidation)*
2. For each node in Vx: (nodeId, validationResults)
3. Client updates UI accordingly 
  
* could be optimized further in the future by only sending updated relevance for Rx/Qx and relevance, required and missing for Rx/Lx
 
 */

/**
 * @author M. Togna
 * 
 */
class SurveyDependencies {

	private Survey survey;

	private StateDependencyMap relevanceDependencies;
	private StateDependencyMap requiredDependencies;
	private StateDependencyMap checkDependencies;

	SurveyDependencies(Survey survey) {
		this.survey = survey;
		relevanceDependencies = new StateDependencyMap(getExpressionFactory(this.survey));
		requiredDependencies = new StateDependencyMap(getExpressionFactory(this.survey));
		checkDependencies = new StateDependencyMap(getExpressionFactory(this.survey));

		registerDependencies();
	}

	Set<NodePathPointer> getCheckDependencies(NodeDefinition definition) {
		String path = definition.getPath();
		return checkDependencies.getDependencySet(path);
	}

	Set<NodePathPointer> getRelevanceDependencies(NodeDefinition definition) {
		String path = definition.getPath();
		return relevanceDependencies.getDependencySet(path);
	}

	Set<NodePathPointer> getRequiredDependencies(NodeDefinition definition) {
		String path = definition.getPath();
		return requiredDependencies.getDependencySet(path);
	}

	private void registerDependencies() {
		Schema schema = survey.getSchema();
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition rootDefn : rootEntityDefinitions) {
			registerDependencies(rootDefn);
		}
	}

	private void registerDependencies(EntityDefinition entityDefinition) {
		List<NodeDefinition> childDefinitions = entityDefinition.getChildDefinitions();
		for (NodeDefinition nodeDefinition : childDefinitions) {
			relevanceDependencies.registerDependencies(nodeDefinition, nodeDefinition.getRelevantExpression());
			requiredDependencies.registerDependencies(nodeDefinition, nodeDefinition.getRequiredExpression());

			if (nodeDefinition instanceof AttributeDefinition) {
				registerDependencies((AttributeDefinition) nodeDefinition);
			} else {
				registerDependencies((EntityDefinition) nodeDefinition);
			}
		}
	}

	private void registerDependencies(AttributeDefinition attributeDefinition) {
		List<Check<?>> checks = attributeDefinition.getChecks();
		for (Check<?> check : checks) {
			checkDependencies.registerDependencies(attributeDefinition, check.getCondition());
			if (check instanceof ComparisonCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getEqualsExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getLessThanExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getLessThanOrEqualsExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getGreaterThanExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getGreaterThanOrEqualsExpression());
			} else if (check instanceof CustomCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((CustomCheck) check).getExpression());
			} else if (check instanceof DistanceCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getDestinationPointExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getMaxDistanceExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getMinDistanceExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getSourcePointExpression());
			} else if (check instanceof UniquenessCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((UniquenessCheck) check).getExpression());
			}
		}
		
		if(attributeDefinition instanceof CodeAttributeDefinition){
			CodeAttributeDefinition codeDefinition = (CodeAttributeDefinition) attributeDefinition;
			checkDependencies.registerDependencies(attributeDefinition, codeDefinition.getParentExpression());
		}
		
	}

	private ExpressionFactory getExpressionFactory(Survey survey) {
		SurveyContext surveyContext = survey.getContext();
		return surveyContext.getExpressionFactory();
	}
	
}
