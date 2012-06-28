package org.openforis.idm.metamodel.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlInit;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.xml.sax.SAXException;

/**
 * @author G. Miceli
 * @author S. Ricci
 * 
 */
public class SurveyUnmarshaller {

	private static final String XML_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";
	private static final String IDML_SCHEMA_RESOURCE_PATH = "/idml3.xsd";
	private Unmarshaller unmarshaller;
	private Class<? extends Survey> surveyClass;
	private SurveyContext surveyContext;
	
	private final Log log = LogFactory.getLog(getClass());

	SurveyUnmarshaller(Unmarshaller unmarshaller, Class<? extends Survey> surveyClass, SurveyContext surveyContext) {
		this.unmarshaller = unmarshaller;
		this.surveyClass = surveyClass;
		this.surveyContext = surveyContext;
	}

	public Survey unmarshal(String filename) throws IOException, InvalidIdmlException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			return unmarshal(bis);
		} finally {
			if ( fis != null ) {
				try {
					fis.close();
				} catch ( IOException e ){
					log.warn("Could not close file: "+e.getMessage());
				}
			}
		}
	}
	
	public Survey unmarshal(byte[] bytes) throws InvalidIdmlException {
		return unmarshal(new ByteArrayInputStream(bytes));
	}
	
	public Survey unmarshal(InputStream is) throws InvalidIdmlException {
		try {
			Unmarshaller.Listener listener = getListener();
			unmarshaller.setListener(listener);
			ValidationEventCollector vec = new ValidationEventCollector();
			unmarshaller.setEventHandler(vec);

			JAXBElement<? extends Survey> jaxbElement = unmarshaller.unmarshal(new StreamSource(is), surveyClass);
			Survey survey = jaxbElement.getValue();
			survey.setSurveyContext(surveyContext);

			if (vec.hasEvents()) {
				throw new InvalidIdmlException(vec.getEvents());
			}
			return survey;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public void validateAgainstSchema(byte[] bytes) throws InvalidIdmlException {
		validateAgainstSchema(new ByteArrayInputStream(bytes));
	}
	
	public void validateAgainstSchema(InputStream is) throws InvalidIdmlException {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XML_SCHEMA_LANGUAGE);
			URL schemaLocation = getClass().getResource(IDML_SCHEMA_RESOURCE_PATH);
			Schema schema = factory.newSchema(schemaLocation);
			javax.xml.validation.Validator validator = schema.newValidator();
			Source source = new StreamSource(is);
			validator.validate(source);
		} catch (IOException e) {
			throw new InvalidIdmlException("Cannot find the xsd to validate the idml");
		} catch (SAXException e) {
			throw new InvalidIdmlException("Error validating the idml against the schema: " + e.getMessage());
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
