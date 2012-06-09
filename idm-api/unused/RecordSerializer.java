package org.openforis.idm.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.compress.DeflateCompressor;
import com.esotericsoftware.kryo.serialize.ArraySerializer;
import com.esotericsoftware.kryo.serialize.ClassSerializer;
import com.esotericsoftware.kryo.serialize.CollectionSerializer;
import com.esotericsoftware.kryo.serialize.FieldSerializer;
import com.esotericsoftware.kryo.serialize.MapSerializer;
import com.esotericsoftware.kryo.serialize.ReferenceFieldSerializer;

/**
 * 
 * @author G. Miceli
 *
 */
public class RecordSerializer<T extends Record> {

	private Kryo kryo;
	private int bufferSize;
	private Class<T> clazz;

	public RecordSerializer(Class<T> clazz, int bufferSize) {
		this.clazz= clazz;
		this.kryo = new Kryo();
		registerDefaults(clazz);
//		registerDeflated(clazz);
		this.bufferSize = bufferSize;
		
	}

	private void registerDefaults(Class<T> clazz) {
		kryo.register(clazz, new ReferenceFieldSerializer(kryo, clazz));
		kryo.register(ArrayList.class, new CollectionSerializer(kryo));
		kryo.register(AttributeField.class);
		kryo.register(Class.class, new ClassSerializer(kryo));
		kryo.register(AttributeField[].class, new ArraySerializer(kryo));
		kryo.register(HashMap.class, new MapSerializer(kryo));
		kryo.register(ModelVersion.class);
		kryo.register(Entity.class, new ReferenceFieldSerializer(kryo, Entity.class));
		kryo.register(CodeAttribute.class);
		kryo.register(CoordinateAttribute.class);
		kryo.register(BooleanAttribute.class);
		kryo.register(IntegerAttribute.class);
		kryo.register(IntegerRangeAttribute.class);
		kryo.register(RealAttribute.class);
		kryo.register(RealRangeAttribute.class);
		kryo.register(DateAttribute.class);
		kryo.register(TaxonAttribute.class);
		kryo.register(TextAttribute.class);
		kryo.register(TimeAttribute.class);
		kryo.register(FileAttribute.class);
		kryo.register(LanguageSpecificText.class);
	}

	private void registerDeflated(Class<T> clazz) {
		kryo.register(clazz, new DeflateCompressor(new ReferenceFieldSerializer(kryo, clazz), 500000));
		kryo.register(ArrayList.class, new DeflateCompressor(new CollectionSerializer(kryo), 500000));
		kryo.register(Entity.class, new DeflateCompressor(new ReferenceFieldSerializer(kryo, Entity.class), 500000));
		kryo.register(Class.class, new DeflateCompressor(new ClassSerializer(kryo), 500000));
		kryo.register(AttributeField[].class, new DeflateCompressor(new ArraySerializer(kryo), 500000));
		kryo.register(HashMap.class, new DeflateCompressor(new MapSerializer(kryo), 500000));
		registerFieldSerializer(AttributeField.class);
		registerFieldSerializer(ModelVersion.class);
		registerFieldSerializer(CodeAttribute.class);
		registerFieldSerializer(CoordinateAttribute.class);
		registerFieldSerializer(BooleanAttribute.class);
		registerFieldSerializer(IntegerAttribute.class);
		registerFieldSerializer(IntegerRangeAttribute.class);
		registerFieldSerializer(RealAttribute.class);
		registerFieldSerializer(RealRangeAttribute.class);
		registerFieldSerializer(DateAttribute.class);
		registerFieldSerializer(TaxonAttribute.class);
		registerFieldSerializer(TextAttribute.class);
		registerFieldSerializer(TimeAttribute.class);
		registerFieldSerializer(FileAttribute.class);
		registerFieldSerializer(LanguageSpecificText.class);
	}

	private void registerFieldSerializer(Class<?> clazz) {
		kryo.register(clazz, new DeflateCompressor(new FieldSerializer(kryo, clazz), 500000));
	}

	public void serialize(T record, String filename) throws IOException {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(filename));
			serialize(record, os);
		} finally {
			if ( os != null ) {
				os.close();
			}
		}
	}

	public void serialize2(T record, String filename) throws IOException {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(filename));
			serialize2(record, os);
		} finally {
			if ( os != null ) {
				os.close();
			}
		}
	}

	/**
	 * Remember to close the output stream in outer finally clause to avoid
	 * memory leaks! 
	 * @param record
	 * @param os
	 * @throws IOException
	 */
	public void serialize(T record, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(record);
	}

	public void serialize2(T record, OutputStream os) throws IOException {
		ObjectBuffer buffer = new ObjectBuffer(kryo, bufferSize);
		buffer.writeObject(os, record);
	}
//
//	private class NodeSerializer extends ReferenceFieldSerializer {
//		public NodeSerializer(Kryo kryo, Class<?> type) {
//			super(kryo, type);
//		}
//
//		@Override
//		@SuppressWarnings( "unchecked" )
//		public <T> T newInstance(Kryo kryo, Class<T> type) {
//			// TODO Auto-generated method stub
//			return super.newInstance(kryo, type);
//	        Constructor<?> constructor = _constructors.get( type );
//	        if ( constructor == null ) {
//	            constructor = getNoArgsConstructor( type );
//	            if ( constructor == null ) {
//	                constructor = newConstructorForSerialization( type );
//	            }
//	            _constructors.put( type, constructor );
//	        }
//	        return (T) newInstanceFrom( constructor );
//	    }
//	}
	
	public T deserialize(RecordContext recordContext, Survey survey, String filename) throws IOException {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filename));
			T record = deserialize(recordContext, survey, is);
			record.afterDeserialize(recordContext, survey);
			return record;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public T deserialize2(RecordContext recordContext, Survey survey, String filename) throws IOException {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filename));
			T record = deserialize2(recordContext, survey, is);
			record.afterDeserialize(recordContext, survey);
			return record;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * Remember to close the input stream in outer finally clause to avoid
	 * memory leaks!
	 * 
	 * @param record
	 * @param os
	 * @throws IOException
	 */
	public T deserialize(RecordContext recordContext, Survey survey, InputStream is) throws IOException {
		Object o;
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			o = ois.readObject();
			T record = (T) o;
			Entity rootEntity = record.getRootEntity();
			if (rootEntity == null) {
				throw new IllegalStateException("Failed to load record; root entity was null");
			}
			record.afterDeserialize(recordContext, survey);
			return record;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Stream returned wrong type of object: " + e.getMessage());
		}
	}

	/**
	 * Remember to close the input stream in outer finally clause to avoid
	 * memory leaks!
	 * 
	 * @param record
	 * @param os
	 * @throws IOException
	 */
	public T deserialize2(RecordContext recordContext, Survey survey, InputStream in) throws IOException {
		ObjectBuffer buffer = new ObjectBuffer(kryo, bufferSize);
		T record = buffer.readObject(in, clazz);
		Entity rootEntity = record.getRootEntity();
		if (rootEntity == null) {
			throw new IllegalStateException("Failed to load record; root entity was null");
		}
		record.afterDeserialize(recordContext, survey);
		return record;
	}

}
