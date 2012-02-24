package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.stream.StreamSource;

import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlInit;
import org.openforis.idm.metamodel.xml.internal.XmlParent;

/**
 * @author G. Miceli
 */
public class SurveyUnmarshaller {

	private Unmarshaller unmarshaller;
	private Class<? extends Survey> surveyClass;

	SurveyUnmarshaller(Unmarshaller unmarshaller, Class<? extends Survey> surveyClass) {
		super();
		this.unmarshaller = unmarshaller;
		this.surveyClass = surveyClass;
	}

	public Survey unmarshal(InputStream is) throws IOException, InvalidIdmlException {
		try {
			Unmarshaller.Listener listener = getListener();
			unmarshaller.setListener(listener);
			ValidationEventCollector vec = new ValidationEventCollector();
			unmarshaller.setEventHandler(vec);

			JAXBElement<? extends Survey> jaxbElement = unmarshaller.unmarshal(new StreamSource(is), surveyClass);
			Survey survey = jaxbElement.getValue();

			if (vec.hasEvents()) {
				throw new InvalidIdmlException(vec.getEvents());
			}
			return survey;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	protected Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	protected Unmarshaller.Listener getListener() {
		return new Listener();
	}

	private class Listener extends Unmarshaller.Listener {
		@Override
		public void beforeUnmarshal(Object target, Object parent) {
			Class<?> targetClass = target.getClass();
			while (!Object.class.equals(targetClass)) {
				Field[] targetFields = targetClass.getDeclaredFields();
				for (Field targetField : targetFields) {
					XmlParent xmlParentAnnotation = targetField.getAnnotation(XmlParent.class);
					if (xmlParentAnnotation != null) {
						setParentReference(targetField, target, parent);
					}

					XmlInherited xmlInheritedAnnotation = targetField.getAnnotation(XmlInherited.class);
					if (xmlInheritedAnnotation != null) {
						setInheritedReference(xmlInheritedAnnotation.value()[0], targetField, target, parent);
					}
				}
				targetClass = targetClass.getSuperclass();
			}
		}

		@Override
		public void afterUnmarshal(Object target, Object parent) {
			Class<?> targetClass = target.getClass();
			while (!Object.class.equals(targetClass)) {
				Method[] targetMethods = targetClass.getDeclaredMethods();
				for (Method targetMethod : targetMethods) {
					XmlInit ann = targetMethod.getAnnotation(XmlInit.class);
					if (ann != null) {
						try {
							targetMethod.setAccessible(true);
							targetMethod.invoke(target);
						} catch (InvocationTargetException e) {
							throw new IdmInterpretationError("Error initializing object after unmarshalling", e);
						} catch (IllegalAccessException e) {
							throw new IdmInterpretationError("Error initializing object after unmarshalling", e);
						} finally {
							targetMethod.setAccessible(false);
						}
					}
				}
				targetClass = targetClass.getSuperclass();
			}
		}
	}

	private static void setParentReference(Field targetField, Object target, Object parent) {
		if (targetField.getType().isAssignableFrom(parent.getClass())) {
			targetField.setAccessible(true);
			try {
				// Set parent into field with annotation
				targetField.set(target, parent);
			} catch (IllegalAccessException e) {
				throw new IdmInterpretationError("Error unmarshalling parent reference", e);
			} finally {
				targetField.setAccessible(false);
			}
		}
	}

	private static void setInheritedReference(String parentFieldName, Field targetField, Object target, Object parent) {
		Field sourceField = findField(parentFieldName, parent);
		if (sourceField == null) {
			return;
		}
		try {
			sourceField.setAccessible(true);
			targetField.setAccessible(true);

			// Copy value from field in parent into field with annotation
			Object inheritedValue = sourceField.get(parent);
			targetField.set(target, inheritedValue);
		} catch (IllegalAccessException e) {
			throw new IdmInterpretationError("Error unmarshalling inherited reference", e);
		} finally {
			sourceField.setAccessible(false);
			targetField.setAccessible(false);
		}
	}

	private static Field findField(String name, Object parent) {
		Class<?> parentClass = parent.getClass();
		while (!Object.class.equals(parentClass)) {
			try {
				Field field = parentClass.getDeclaredField(name);
				if (field != null) {
					return field;
				}
			} catch (NoSuchFieldException e) {
			}
			parentClass = parentClass.getSuperclass();
		}
		return null;
	}

}
