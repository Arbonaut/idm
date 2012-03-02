package org.openforis.idm.model;

import java.io.IOException;
import java.util.Collection;

import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Pipe;
import com.dyuproject.protostuff.WireFormat.FieldType;
import com.dyuproject.protostuff.runtime.MappedSchema;

@SuppressWarnings("rawtypes")
public abstract class NodeSchema<T extends Node> extends MappedSchema<T> {

	public NodeSchema(Class<T> clazz, Collection<Field<T>> fields) {
		super(clazz, fields, fields.size());
	}

	@Override
	public boolean isInitialized(T entity) {
		return entity.definitionId != null;
	}

	protected static class DefinitionIdField<N extends Node> extends Field<N> {
	
		public DefinitionIdField() {
			super(FieldType.UINT32, 1, "definitionId");
		}
	
		@Override
		protected void writeTo(Output output, N entity) throws IOException {
			try {
				output.writeUInt32(1, entity.definitionId, false);
			} catch ( NullPointerException e ) {
				throw new IllegalArgumentException("Cannot serialize unintialized Entity", e);
			}
		}
	
		@Override
		@SuppressWarnings("unchecked")
		protected void mergeFrom(Input input, N node) throws IOException {
			try {
				node.definitionId = input.readUInt32();
				Record record = node.getRecord();
				Survey survey = record.getSurvey();
				Schema schema = survey.getSchema();
				node.definition = schema.getById(node.definitionId);
				if ( node.definition == null ) {
					throw new IllegalStateException("Definition not found for this node");
				}
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
}
	