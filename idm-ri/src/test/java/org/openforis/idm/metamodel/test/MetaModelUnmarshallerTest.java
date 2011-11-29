/**
 * 
 */
package org.openforis.idm.metamodel.test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller.Listener;

import org.junit.Test;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.impl.MetaModelUnmarshallerListener;
import org.openforis.idm.metamodel.impl.SurveyImpl;
import org.openforis.idm.util.XmlBindingUtil;

/**
 * @author M. Togna
 * 
 */
public class MetaModelUnmarshallerTest {

	@Test
	public void unmarshallMetaModelTest() throws JAXBException, IOException {
		Class<SurveyImpl> clazz = SurveyImpl.class;
		String filename = "/home/minotogna/dev/projects/faofin/tz/tanzania-naforma.idm.xml";
		Listener listener = new MetaModelUnmarshallerListener();
		SurveyImpl survey = XmlBindingUtil.unmarshall(clazz, filename, listener);

		String name = survey.getName();
		System.err.println("Survey name " + name);

		Schema schema = survey.getModel().getSchema();
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition entityDefinition : rootEntityDefinitions) {

			System.err.println("root entity: "+ entityDefinition.getName());
			
			List<ModelObjectDefinition> childDefinitions = entityDefinition.getChildDefinitions();
			for (ModelObjectDefinition modelObjectDefinition : childDefinitions) {
				System.out.println(modelObjectDefinition.getName());
			}

		}

	}

}
