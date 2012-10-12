package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel;

/**
 * 
 * @author G. Miceli
 *
 */
public abstract class AbstractNodeDefinitionIM<T extends NodeDefinition, P> extends VersionableSurveyObjectMarshaller<T, P> {

	protected AbstractNodeDefinitionIM(String tag) {
		super(tag);
		addChildMarshallers(
				new LabelIM(),
				new DescriptionIM());
	}
	
	@Override
	protected void attributes(T defn) throws IOException {
		attribute(ID, defn.getId());
		attribute(NAME, defn.getName());
		super.attributes(defn);
	}
	
	private class LabelIM extends LanguageSpecificTextIM<T> {

		public LabelIM() {
			super(LABEL);
		}
		
		@Override
		protected void attributes(LanguageSpecificText txt) throws IOException {
			NodeLabel label = (NodeLabel) txt;
			attribute(TYPE, label.getType().name().toLowerCase());
			super.attributes(txt);
		}
		
		@Override
		protected void marshalInstances(T defn) throws IOException {
			marshal(defn.getLabels());
		}
	}
	
	private class DescriptionIM extends LanguageSpecificTextIM<T> {

		public DescriptionIM() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(T defn) throws IOException {
			marshal(defn.getDescriptions());
		}
	}
}
