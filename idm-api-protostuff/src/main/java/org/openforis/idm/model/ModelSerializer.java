package org.openforis.idm.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	private static EntitySchema ENTITY_SCHEMA;

	static {
		/* Important: Schemas must be registered in depth-first post-order!!! */
		RuntimeSchema.register(AttributeField.class, new AttributeFieldSchema());
		register(new AttributeSchema(BooleanAttribute.class));
		register(new AttributeSchema(CodeAttribute.class));
		register(new AttributeSchema(CoordinateAttribute.class));
		register(new AttributeSchema(DateAttribute.class));
		register(new AttributeSchema(FileAttribute.class));
		register(new AttributeSchema(IntegerAttribute.class));
		register(new AttributeSchema(IntegerRangeAttribute.class));
		register(new AttributeSchema(RealAttribute.class));
		register(new AttributeSchema(RealRangeAttribute.class));
		register(new AttributeSchema(TaxonAttribute.class));
		register(new AttributeSchema(TextAttribute.class));
		register(new AttributeSchema(TimeAttribute.class));
		ENTITY_SCHEMA = new EntitySchema();
		RuntimeSchema.register(Entity.class, ENTITY_SCHEMA);
	}

	private static void register(AttributeSchema schema) {
		RuntimeSchema.register(schema.typeClass(), schema);
	}

	private LinkedBuffer buffer;
	
	public ModelSerializer(int bufferSize) {
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}
	
	public byte[] toByteArray(Entity entity) {
		return ProtostuffIOUtil.toByteArray(entity, ENTITY_SCHEMA, buffer);
	}
	
	public void writeTo(OutputStream output, Entity entity) throws IOException {
//		XmlIOUtil.writeTo(output, entity, ENTITY_SCHEMA);		
		ProtostuffIOUtil.writeTo(output, entity, ENTITY_SCHEMA, buffer);
	}
	
	public void writeTo(String filename, Entity entity) throws IOException {
		OutputStream out = new FileOutputStream(filename);
		try {
			writeTo(out, entity);
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void mergeFrom(byte[] data, Entity entity) throws IOException {
		ProtostuffIOUtil.mergeFrom(data, entity, ENTITY_SCHEMA);
	}
	
	public void mergeFrom(InputStream input, Entity entity) throws IOException {
		long start = System.currentTimeMillis();
//		XmlIOUtil.mergeFrom(input, entity, ENTITY_SCHEMA);
		ProtostuffIOUtil.mergeFrom(input, entity, ENTITY_SCHEMA);
		long dur = System.currentTimeMillis() - start;
		System.out.println(dur);
//		afterMerge(entity);
	}

	public void mergeFrom(String filename, Entity entity) throws IOException {
		InputStream in = new FileInputStream(filename);
		try {
			mergeFrom(in, entity);
		} finally {
			in.close();
		}
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
