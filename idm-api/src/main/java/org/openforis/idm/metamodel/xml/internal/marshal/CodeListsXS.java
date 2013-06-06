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
class CodeListsXS extends VersionableSurveyObjectXS<CodeList, Survey> {

	CodeListsXS() {
		super(LIST);
		setListWrapperTag(CODE_LISTS);
		addChildMarshallers(
				new LabelXS(), 
				new DescriptionXS(), 
				new CodingSchemeXS(), 
				new HierarchyXS(),
				new ItemsXS());
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
	
	private class LabelXS extends LanguageSpecificTextXS<CodeList> {

		public LabelXS() {
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
	
	private class DescriptionXS extends LanguageSpecificTextXS<CodeList> {

		public DescriptionXS() {
			super(DESCRIPTION);
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getDescriptions());
		}
	}

	private class CodingSchemeXS extends XmlSerializerSupport<CodeScope, CodeList> {

		public CodingSchemeXS() {
			super(CODING_SCHEME);
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getCodeScope());
		}
		
		protected void attributes(CodeScope scope) throws IOException {
			attribute(SCOPE, scope == null ? null : scope.name().toLowerCase());
		}
	}

	private class HierarchyXS extends XmlSerializerSupport<CodeListLevel, CodeList> {

		public HierarchyXS() {
			super(LEVEL);
			setListWrapperTag(HIERARCHY);
			addChildMarshallers(new LabelXS(), new DescriptionXS());
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getHierarchy());
		}

		@Override
		protected void attributes(CodeListLevel level) throws IOException {
			attribute(NAME, level.getName());
		}
		
		private class LabelXS extends LanguageSpecificTextXS<CodeListLevel> {

			public LabelXS() {
				super(LABEL);
			}
			
			@Override
			protected void marshalInstances(CodeListLevel level) throws IOException {
				marshal(level.getLabels());
			}
		}
		
		private class DescriptionXS extends LanguageSpecificTextXS<CodeListLevel> {

			public DescriptionXS() {
				super(DESCRIPTION);
			}
			
			@Override
			protected void marshalInstances(CodeListLevel level) throws IOException {
				marshal(level.getDescriptions());
			}
		}
		
	}

	private abstract class AbstractItemXS<P> extends XmlSerializerSupport<CodeListItem, P> {

		public AbstractItemXS() {
			super(ITEM);
			addChildMarshallers(new CodeXS(), new LabelXS(), new DescriptionXS());
		}

		@Override
		protected void attributes(CodeListItem item) throws IOException {
			attribute(ID, item.getId());
			if ( item.isQualifiable() ) {
				attribute(QUALIFIABLE, "true");
			}
			attribute(SINCE, item.getSinceVersionName());
			attribute(DEPRECATED, item.getDeprecatedVersionName());
		}
		
		private class CodeXS extends TextXS<CodeListItem> {

			public CodeXS() {
				super(CODE);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getCode());
			}
		}
		
		private class LabelXS extends LanguageSpecificTextXS<CodeListItem> {

			public LabelXS() {
				super(LABEL);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getLabels());
			}
		}
		
		private class DescriptionXS extends LanguageSpecificTextXS<CodeListItem> {

			public DescriptionXS() {
				super(DESCRIPTION);
			}
			
			@Override
			protected void marshalInstances(CodeListItem item) throws IOException {
				marshal(item.getDescriptions());
			}
		}
		
	}
	
	private class ItemsXS extends AbstractItemXS<CodeList> {
		
		public ItemsXS() {
			setListWrapperTag(ITEMS);
			addChildMarshallers(new ChildItemXS());
		}
		
		@Override
		protected void marshalInstances(CodeList list) throws IOException {
			marshal(list.getItems());
		}
	}

	private class ChildItemXS extends AbstractItemXS<CodeListItem> {
		
		public ChildItemXS() {
			addChildMarshallers(this);
		}
		
		@Override
		protected void marshalInstances(CodeListItem item) throws IOException {
			marshal(item.getChildItems());
		}
	}
}
