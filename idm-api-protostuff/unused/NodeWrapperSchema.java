package org.openforis.idm.model;

import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.ProtostuffException;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.esotericsoftware.minlog.Log;

/**
 * @author G. Miceli
 */
@SuppressWarnings({"unchecked", "rawtypes"})
class NodeWrapperSchema extends SchemaSupport<NodeWrapper> {

	private Entity parent;
	private Schema idmSchema;
	
	public NodeWrapperSchema(Entity parent) {
		super(NodeWrapper.class, "id", "data");
		this.parent = parent;
		if ( parent == null ) {
			throw new IllegalArgumentException("Null parent");
		}
		this.idmSchema = parent.getSchema();
		if ( idmSchema == null ) {
			throw new IllegalArgumentException("No schema attached to entity");
		}
	}

	@Override
	public boolean isInitialized(NodeWrapper wrapper) {
		return wrapper.definitionId != null;
	}

	@Override
	public NodeWrapper newMessage() {
		return new NodeWrapper();
	}

	@Override
	public String messageName() {
		return "node";
	}

	@Override
	public void writeTo(Output output, NodeWrapper wrapper) throws IOException {
		// Id
		output.writeUInt32(1, wrapper.definitionId, false);
		// Node		
		com.dyuproject.protostuff.Schema schema = RuntimeSchema.getSchema(wrapper.node.getClass());
		output.writeObject(2, wrapper.node, schema, false);
	}

	@Override
	public void mergeFrom(Input input, NodeWrapper wrapper) throws IOException {
		// Id
		readAndCheckFieldNumber(input, 1);
		wrapper.definitionId = input.readUInt32();
		NodeDefinition defn = idmSchema.getById(wrapper.definitionId);
		if ( defn == null ) {
			throw new ProtostuffException("Unknown definition "+wrapper.definitionId);
		}
		wrapper.node = defn.createNode();
		parent.add(wrapper.node);
		
		// Node
		readAndCheckFieldNumber(input, 2);
		com.dyuproject.protostuff.Schema schema = RuntimeSchema.getSchema(wrapper.node.getClass());
		input.mergeObject(wrapper.node, schema);
		
		readAndCheckFieldNumber(input, 0);
	}

	protected void readAndCheckFieldNumber(Input input, int i) throws IOException, ProtostuffException {
		if ( input.readFieldNumber(this) != i ) {
			throw new ProtostuffException("Corrupt field number");
		}
	}
}


