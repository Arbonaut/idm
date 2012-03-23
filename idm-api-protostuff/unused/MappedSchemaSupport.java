package org.openforis.idm.model;

import com.dyuproject.protostuff.runtime.MappedSchema;
import com.dyuproject.protostuff.runtime.RuntimeFieldFactory;
import com.dyuproject.protostuff.runtime.RuntimeReflectionFieldFactory;

/**
 * @author G. Miceli
 */
public abstract class MappedSchemaSupport<T> extends MappedSchema<T> {
	
	public MappedSchemaSupport(Fields<T> fields) {
		super(fields.typeClass, fields.fieldArray, fields.fieldArray.length + 1);
	}

	@SuppressWarnings("unchecked")
	protected static class Fields<T> {
		
		private RuntimeFieldFactory<T> fieldFactory = null;
		private Field<T> fieldArray[];
		private Class<T> typeClass;
		
		public Fields(Class<T> typeClass, int numFields) {
			this.typeClass = typeClass;
			this.fieldArray = new Field[numFields];
		}

		public void set(int number, String name) {
			MappedSchema.Field<T> protoField = create(number, name);
			fieldArray[number-1] = protoField;
		}

		private MappedSchema.Field<T> create(int number, String name) {
			if ( fieldFactory == null ) {
				fieldFactory = (RuntimeFieldFactory<T>) RuntimeFieldFactory.getFieldFactory(typeClass);
			}
			return create(fieldFactory, number, name);
		}
		
		private MappedSchema.Field<T> create(RuntimeFieldFactory<?> fieldFactory, int number, String name) {
			java.lang.reflect.Field javaField = findField(typeClass, name);
			if ( javaField == null ) { 
				throw new RuntimeException("Unknown field '"+name+"'.  Code possibly out of sync with proto schema classes");
			}
			MappedSchema.Field<T> protoField = fieldFactory.create(number, name, javaField);
			return protoField;
		}
		
		private static java.lang.reflect.Field findField(Class<?> cls, String name) {
			try {
				return cls.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				if ( cls == Object.class ) {
					return null;
				} else {
					return findField(cls.getSuperclass(), name);
				} 
			}
		}

		public void set(Field<T> field) {
			fieldArray[field.number-1] = field;
		}

		public void set(RuntimeFieldFactory<?> factory, int number, String name) {
			MappedSchema.Field<T> protoField = create(factory, number, name);
			fieldArray[number-1] = protoField;
		}
	}
}
