/**
 * 
 */
package org.openforis.idm.metamodel.test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller.Listener;

import org.junit.Assert;
import org.junit.Test;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.impl.EntityDefinitionImpl;
import org.openforis.idm.metamodel.impl.MetaModelUnmarshallerListener;
import org.openforis.idm.metamodel.impl.SurveyImpl;
import org.openforis.idm.util.XmlBindingUtil;
import org.w3c.dom.Element;

/**
 * @author M. Togna
 * 
 */
public class MetaModelUnmarshallerTest {

	//@Test
	public void unmarshallMetaModelTest() throws JAXBException, IOException {
		Survey survey = getSurvey();

		String name = survey.getName();
		System.err.println("Survey name " + name);

		Schema schema = survey.getSchema();
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition entityDefinition : rootEntityDefinitions) {
			System.err.println("root entity: " + entityDefinition.getName());
			print(entityDefinition, "");
		}

		Assert.assertTrue(survey.getConfiguration() instanceof Element);
		System.err.println("~~~END");
	}

	/**
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	private Survey getSurvey() throws JAXBException, IOException {
		Class<SurveyImpl> clazz = SurveyImpl.class;
		String filename = "/home/minotogna/dev/projects/faofin/tz/naforma-idm/tanzania-naforma.idm.xml";
		Listener listener = new MetaModelUnmarshallerListener();
		SurveyImpl survey = XmlBindingUtil.unmarshall(clazz, filename, listener);
		return survey;
	}

	private void print(ModelObjectDefinition mod, String p) {
		if (mod instanceof EntityDefinition) {
			System.err.println(p + "Entity " + mod.getName());
			List<ModelObjectDefinition> childDefinitions = ((EntityDefinition) mod).getChildDefinitions();
			for (ModelObjectDefinition modelObjectDefinition : childDefinitions) {
				print(modelObjectDefinition, p + "\t");
			}
		} else if (mod instanceof AttributeDefinition) {
			System.out.println("\t" + p + "Attr: " + mod.getName());
		}

	}

	@Test
	public void jxpathExprTest() throws JAXBException, IOException {
		Survey survey = getSurvey();
		EntityDefinitionImpl entityDefinition = (EntityDefinitionImpl) survey.getSchema().getRootEntityDefinitions().get(0);
		EntityDefinitionImpl plot = (EntityDefinitionImpl) entityDefinition.getChildDefinition("plot");
		ModelObjectDefinition dbh = plot.get("plot/tree/dbh");
		System.out.println(dbh.getName());
		ModelObjectDefinition m2 = dbh.get("parent()");
		System.out.println(m2.getName());

		// Pointer pointer = jxPathContext.getPointer ("../");
		// System.err.println(pointer.getValue().toString());

		// JXPathContext jxPathContext = JXPathContext.newContext(dbh);
		// Object value = jxPathContext.getValue("../../name");
		// System.out.println(value);

	}

}
