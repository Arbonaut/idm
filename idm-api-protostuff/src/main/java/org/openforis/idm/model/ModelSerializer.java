package org.openforis.idm.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @author G. Miceli
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModelSerializer {

	private static com.dyuproject.protostuff.Schema<Entity> SCHEMA;

	static {
		RuntimeSchema.register(AttributeField.class, new AttributeFieldSchema());
		SCHEMA = RuntimeSchema.getSchema(Entity.class);
	}
	
	private LinkedBuffer buffer;
	
	public ModelSerializer(int bufferSize) {
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}
	
	public int writeTo(OutputStream output, Entity entity) throws IOException {
		return ProtostuffIOUtil.writeTo(output, entity, SCHEMA, buffer);		
	}
	
	public void mergeFrom(InputStream input, Entity entity) throws IOException {
		ProtostuffIOUtil.mergeFrom(input, entity, SCHEMA);
		afterMerge(entity);
	}

	protected void afterMerge(Entity entity) {
		final Record record = entity.record;
		final Survey survey = record.getSurvey();
		final Schema schema = survey.getSchema();
		
		entity.traverse(new NodeVisitor() {
			@Override
			public void visit(Node node, int idx) {
				// Init definition
				if ( node.definitionId == null ) {
					throw new IllegalStateException("Definition id not set before saving; cannot deserialize");
				}
				NodeDefinition defn = schema.getById(node.definitionId);
				if ( defn == null ) {
					throw new IllegalStateException("Definition "+node.definitionId+" is not defined in this survey");
				}
				try {
					node.definition = defn;
				} catch ( ClassCastException e ) {
					throw new IllegalStateException("Definition "+node.definitionId+" is of wrong type "+defn.getClass().getSimpleName());			
				}
				// Init record
				node.setRecord(record);
				// Init entity child parents
				if ( node instanceof Entity ) {
					Entity entity = (Entity) node;
					Collection<ArrayList<Node<? extends NodeDefinition>>> childLists = entity.childrenByName.values();
					for (ArrayList<Node<? extends NodeDefinition>> list : childLists) {
						for (Node<? extends NodeDefinition> child : list) {
							child.parent = entity;
						}
					}
				}
			}
		});
	}

	public void clearBuffer() {
		buffer.clear();
	}
}
