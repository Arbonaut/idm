/**
 * 
 */
package org.openforis.idm.model.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * @author M. Togna
 * 
 */
@Deprecated
public class NodeStateManager {

	private Map<String, ModelDependencies> modelDependenciesMap;
	//private Map<Node<?>, State> nodeStateMap;
	private Validator validator;

	public NodeStateManager() {
		modelDependenciesMap = new HashMap<String, ModelDependencies>();
		//nodeStateMap = new HashMap<Node<?>, State>();
	}

//	public State get(Node<?> node) {
//		return nodeStateMap.get(node);
//	}

	public List<NodeState> refresh(Node<?> node) {
		List<NodeState> nodeStates = new ArrayList<NodeState>();

		NodeState nodeState = refreshState(node);
		nodeStates.add(nodeState);

		refreshDependentNodesState(node, nodeStates);
		return nodeStates;
	}

	public List<NodeState> delete(Node<?> node) {
		
		List<NodeState> nodeStates = new ArrayList<NodeState>();
		refreshDependentNodesState(node, nodeStates);
		return nodeStates;
	}

	private void refreshDependentNodesState(Node<?> node, List<NodeState> nodeStates) {
		ModelDependencies dependencies = getModelDependencies(node);
		Set<Node<?>> dependentNodes = dependencies.getDependantNodes(node);
		for (Node<?> dependentNode : dependentNodes) {
			NodeState state = refreshState(dependentNode);
			nodeStates.add(state);
		}
	}

	private NodeState refreshState(Node<?> node) {
		NodeState nodeState = new NodeState(node);
		nodeState.update(getValidator());
		//nodeStateMap.put(node, nodeState);
		return nodeState;
	}

	protected ModelDependencies getModelDependencies(Node<?> node) {
		Record record = node.getRecord();
		Survey survey = record.getSurvey();
		String surveyName = survey.getName();
		ModelDependencies modelDependencies = modelDependenciesMap.get(surveyName);
		if (modelDependencies == null) {
			SurveyContext recordContext = record.getSurveyContext();
			modelDependencies = new ModelDependencies(recordContext.getExpressionFactory());
			modelDependencies.register(survey);
			modelDependenciesMap.put(surveyName, modelDependencies);
		}
		return modelDependencies;
	}

	public Validator getValidator() {
		if (validator == null) {
			validator = new Validator();
		}
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
