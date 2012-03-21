package org.openforis.idm.model;

import java.io.IOException;
import java.util.List;

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
		super(Entity.class, "children");
	}

	@Override
	public String messageName() {
		return "entity";
	}

	@Override
	public void writeTo(Output out, Entity entity) throws IOException {
		List<Node<? extends NodeDefinition>> children = entity.getChildren();
        for(Node<?> node : children) {
			out.writeUInt32(1, node.definitionId, false);
			out.writeObject(2, node, getSchema(node.getClass()), false);
			
			State childState = entity.getChildState(node.getName());
			out.writeInt32(3, childState.intValue(), false);
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
        		
        		//Node state
        		readAndCheckFieldNumber(input, 3);
        		int intState = input.readInt32();
        		State nodeState = State.parseState(intState);
				entity.childStates.put(node.getName(), nodeState);
        	} else {
            	throw new ProtostuffException("Unexpected field number");
            }
        }
	}
}
