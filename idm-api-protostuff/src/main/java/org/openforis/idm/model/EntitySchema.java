package org.openforis.idm.model;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.ProtostuffException;

/**
 * @author G. Miceli
 */
@SuppressWarnings("unchecked")
public class EntitySchema extends SchemaSupport<Entity> {

	public EntitySchema() {
		super(Entity.class, "children", "childStates");
	}

	@Override
	public String messageName() {
		return "entity";
	}

	@Override
	public void writeTo(Output out, Entity entity) throws IOException {
		List<Node<? extends NodeDefinition>> children = entity.getChildren();
        for(Node<?> node : children) {
        	if(isNodeToBeSaved(node)) {
				out.writeUInt32(1, node.definitionId, false);
				out.writeObject(2, node, getSchema(node.getClass()), false);
        	}
        }
        EntityDefinition definition = entity.getDefinition();
        List<NodeDefinition> childDefinitions = definition.getChildDefinitions();
        for (NodeDefinition childDefinition : childDefinitions) {
        	String childName = childDefinition.getName();
        	State childState = entity.getChildState(childName);
        	out.writeInt32(3, childState.intValue(), false);
        	out.writeString(4, childName, false);
        }
	}

	@Override
	public void mergeFrom(Input input, Entity entity) throws IOException {
        for(int number = input.readFieldNumber(this); ; number = input.readFieldNumber(this))
        {
        	if ( number == 0 ) {
        		break;
        	} else if ( number == 1 ) {
        		Schema idmSchema = entity.getSchema();
        		
        		// Definition id
        		int definitionId = input.readUInt32();
        		NodeDefinition defn = idmSchema.getById(definitionId);
        		if ( defn == null ) {
        			throw new ProtostuffException("Invalid definition id "+definitionId);
        		}
        		Node<?> node = defn.createNode();
        		entity.add(node);
        		
        		// Node
        		readAndCheckFieldNumber(input, 2);
        		input.mergeObject(node, getSchema(node.getClass()));
        		
        	} else if ( number == 3 ){
        		//Node state
        		int intState = input.readInt32();
        		State state = State.parseState(intState);
        		readAndCheckFieldNumber(input, 4);
        		String childName = input.readString();
        		entity.childStates.put(childName, state);
        	} else {
            	throw new ProtostuffException("Unexpected field number");
            }
        }
	}
	
	protected boolean isNodeToBeSaved(Node<?> node) {
		if ( node instanceof Attribute<?, ?> ) {
			Entity parent = node.getParent();
    		int count = parent.getCount(node.getName());
    		if ( count == 1 && ! ((Attribute<?, ?>) node).hasData() ) {
    			return false;
    		}
    	}
		return true;
	}
	
}
