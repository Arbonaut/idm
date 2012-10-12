package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;

/**
 * 
 * @author G. Miceli
 *
 */
class CodeListsIM extends VersionableSurveyObjectMarshaller<CodeList, Survey> {

	CodeListsIM() {
		super(LIST);
		setListWrapperTag(CODE_LISTS);
		addChildMarshallers(
				new LabelIM(), 
				new DescriptionIM(), 
				new CodingSchemeIM(), 
				new HierarchyIM(),
				new ItemsIM());
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		List<CodeList> lists = survey.getCodeLists();
		marshal(lists);
	}
	
	@Override
	protected void attributes(CodeList list) throws IOException {
		attribute(ID, list.getId());
		attribute(NAME, list.getName());
		attribute(LOOKUP, list.getLookupTable());
		super.attributes(list);
	}
	
	private class LabelIM extends LanguageSpecificTextIM<CodeList> {

		public LabelIM() {
			super(LABEL);
		}
		
		@Override
		protected void attributes(LanguageSpecificText txt) throws IOException {
			CodeListLabel label = (CodeListLabel) txt;
			attribute(TYPE, label.getType().name().toLowerCase());
			super.attributes(txt);
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getLabels());
		}
	}
	
	private class DescriptionIM extends LanguageSpecificTextIM<CodeList> {

		public DescriptionIM() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getDescriptions());
		}
	}

	private class CodingSchemeIM extends AbstractIdmlMarshaller<CodeScope, CodeList> {

		public CodingSchemeIM() {
			super(CODING_SCHEME);
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getCodeScope());
		}
		
		protected void attributes(CodeScope scope) throws IOException {
			attribute(SCOPE, scope.name().toLowerCase());
		}
	}

	private class HierarchyIM extends AbstractIdmlMarshaller<CodeListLevel, CodeList> {

		public HierarchyIM() {
			super(LEVEL);
			setListWrapperTag(HIERARCHY);
			addChildMarshallers(new LabelIM(), new DescriptionIM());
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getHierarchy());
		}

		@Override
		protected void attributes(CodeListLevel level) throws IOException {
			attribute(NAME, level.getName());
		}
		
		private class LabelIM extends LanguageSpecificTextIM<CodeListLevel> {

			public LabelIM() {
				super(LABEL);
			}
			
			@Override
			protected void marshalInstances(CodeListLevel level) throws IOException {
				marshal(level.getLabels());
			}
		}
		
		private class DescriptionIM extends LanguageSpecificTextIM<CodeListLevel> {

			public DescriptionIM() {
				super(DESCRIPTION);
			}
			
			@Override
			protected void marshalInstances(CodeListLevel level) throws IOException {
				marshal(level.getDescriptions());
			}
		}
		
	}

	private abstract class AbstractItemIM<P> extends AbstractIdmlMarshaller<CodeListItem, P> {

		public AbstractItemIM() {
			super(ITEM);
			addChildMarshallers(new CodeIM(), new LabelIM(), new DescriptionIM());
		}

		@Override
		protected void attributes(CodeListItem item) throws IOException {
			attribute(ID, item.getId());
			if ( item.isQualifiable() ) {
				attribute(QUALIFIABLE, "true");
			}
		}
		
		private class CodeIM extends TextIM<CodeListItem> {

			public CodeIM() {
				super(CODE);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getCode());
			}
		}
		
		private class LabelIM extends LanguageSpecificTextIM<CodeListItem> {

			public LabelIM() {
				super(LABEL);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getLabels());
			}
		}
		
		private class DescriptionIM extends LanguageSpecificTextIM<CodeListItem> {

			public DescriptionIM() {
				super(DESCRIPTION);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getDescriptions());
			}
		}
		
	}
	
	private class ItemsIM extends AbstractItemIM<CodeList> {
		
		public ItemsIM() {
			setListWrapperTag(ITEMS);
			addChildMarshallers(new ChildItemIM());
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getItems());
		}
	}

	private class ChildItemIM extends AbstractItemIM<CodeListItem> {
		
		public ChildItemIM() {
			addChildMarshallers(this);
		}
		
		@Override
		protected void marshalInstances(CodeListItem item) throws IOException {
			marshal(item.getChildItems());
		}
	}
}
