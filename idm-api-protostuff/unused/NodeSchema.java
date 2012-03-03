package org.openforis.idm.model;


/**
 * @author G. Miceli
 */
@SuppressWarnings("rawtypes")
public abstract class NodeSchema<T extends Node> extends MappedSchemaSupport<T> {

	public NodeSchema(Fields<T> fields) {
		super(fields);
	}

	@Override
	public boolean isInitialized(T entity) {
		return entity.definitionId != null;
	}
	/*
	protected static class DefinitionIdField<V extends Node> extends Field<V> {
		
		public DefinitionIdField(int number, String name) {
			super(FieldType.UINT32, number, name);
		}
	
		@Override
		protected void writeTo(Output output, V node) throws IOException {
			try {
				output.writeUInt32(1, node.definitionId, false);
			} catch ( NullPointerException e ) {
				throw new IllegalArgumentException("Cannot serialize unintialized Entity", e);
			}
		}
	
		@Override
		@SuppressWarnings("unchecked")
		protected void mergeFrom(Input input, V node) throws IOException {
			try {
				node.definitionId = input.readUInt32();
//				Schema schema = node.getSchema();
//				node.definition = schema.getById(node.definitionId);
//				if ( node.definition == null ) {
//					throw new IllegalStateException("Definition not found for this node");
//				}
			} catch ( NullPointerException e ) {
				throw new IllegalArgumentException("Cannot deserialize into unintialized Record", e);
			} catch ( ClassCastException e ) {
				throw new IllegalStateException("Definition serialized is of wrong type", e);
			}
	
		}
	
		@Override
		protected void transfer(Pipe pipe, Input input, Output output, boolean repeated) throws IOException {
			output.writeUInt32(number, input.readUInt32(), repeated);
		}
	}
	*/
}
	