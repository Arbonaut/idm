package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class CodeListsPR extends IdmlPullReader {
	

	public CodeListsPR() {
		super(CODE_LISTS, 1);
		addChildPullReaders(new CodeListPR());
	}

	private class CodeListPR extends IdmlPullReader {
		
		private CodeList list;
		
		public CodeListPR() {
			super(LIST);
			addChildPullReaders(new LabelPR(), new CodingSchemePR(), new HierarchyPR(), new DescriptionPR(), new ItemsPR());
		}
		
		@Override
		protected void onStartTag() throws XmlParseException {
			int id = getIntegerAttribute(ID, true);
			String name = getAttribute(NAME, false);
			String lookupTable = getAttribute(LOOKUP, false);
			Survey survey = getSurvey();
			list = survey.createCodeList(id);
			list.setName(name);
			list.setLookupTable(lookupTable);
		}

		private class CodingSchemePR extends IdmlPullReader {

			public CodingSchemePR() {
				super(CODING_SCHEME, 1);
			}
			
			@Override
			protected void onStartTag()
					throws XmlParseException, XmlPullParserException, IOException {
				String scopeStr = getAttribute(SCOPE, true);
				try {
					CodeScope scope = CodeList.CodeScope.valueOf(scopeStr.toUpperCase());
					list.setCodeScope(scope);
				} catch ( IllegalArgumentException ex ) {
					throw new XmlParseException(getParser(), "invalid scope "+scopeStr);
				}
			}
		}
		
		private class HierarchyPR extends IdmlPullReader {
			public HierarchyPR() {
				super(HIERARCHY, 1);
				addChildPullReaders(new LevelPR());
			}
			
			private class LevelPR extends IdmlPullReader {
				private CodeListLevel level;
				
				public LevelPR() {
					super(LEVEL);
					addChildPullReaders(new LabelPR(), new DescriptionPR());						
				}
				
				@Override
				protected void onStartTag()
						throws XmlParseException, XmlPullParserException, IOException {
					this.level = new CodeListLevel();
					String name = getAttribute(NAME, true);
					level.setName(name);
				}

				private class LabelPR extends LanguageSpecificTextPR {

					public LabelPR() {
						super(LABEL);
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						level.addLabel(lst);
					}
				}

				private class DescriptionPR extends LanguageSpecificTextPR {
					public DescriptionPR() {
						super(DESCRIPTION);
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						level.addDescription(lst);
					}
				}
				
				@Override
				protected void onEndTag() throws XmlParseException {
					list.addLevel(level);
					this.level = null;
				}
			}
		}			
		
		private class LabelPR extends LanguageSpecificTextPR {
			public LabelPR() {
				super(LABEL, true);
			}
			
			@Override
			protected void processText(String lang, String typeStr, String text) throws XmlParseException {
				try {
					CodeListLabel.Type type = CodeListLabel.Type.valueOf(typeStr.toUpperCase());
					CodeListLabel label = new CodeListLabel(type, lang, text);
					list.addLabel(label);
				} catch (IllegalArgumentException e) {
					throw new XmlParseException(getParser(), "invalid type "+typeStr);
				}
			}
		}

		private class DescriptionPR extends LanguageSpecificTextPR {
			public DescriptionPR() {
				super(DESCRIPTION);
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				list.addDescription(lst);
			}
		}

		private class ItemsPR extends IdmlPullReader {
			
			public ItemsPR() {
				super(ITEMS, 1);
				addChildPullReaders(new ItemPR());
			}
			
			private class ItemPR extends IdmlPullReader {
				private CodeListItem parentItem;
				private CodeListItem item;
				
				public ItemPR() {
					super(ITEM);
					addChildPullReaders(new CodePR(), new LabelPR(), new DescriptionPR());
				} 
				
				public ItemPR(CodeListItem parentItem) {
					this();
					this.parentItem = parentItem;
				}

				@Override
				protected void onStartTag()
						throws XmlParseException, XmlPullParserException, IOException {
					int id = getIntegerAttribute(ID, true);
					Boolean q = getBooleanAttribute(QUALIFIABLE, false);
					this.item = list.createItem(id);
					item.setQualifiable(q==null ? false : q);
				}
				
				private class CodePR extends TextPullReader {
					public CodePR() {
						super(CODE, 1);
					}
					
					@Override
					protected void processText(String text) {
						item.setCode(text);
					}
				}

				private class LabelPR extends LanguageSpecificTextPR {
					public LabelPR() {
						super(LABEL);
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						item.addLabel(lst);
					}
				}

				private class DescriptionPR extends LanguageSpecificTextPR {
					public DescriptionPR() {
						super(DESCRIPTION);
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						item.addDescription(lst);
					}
				}

				@Override
				protected XmlPullReader getChildPullReader() throws XmlParseException {
					XmlPullParser parser = getParser();
					String name = parser.getName();
					if ( name.equals(ITEM) ) {
						return new ItemPR(item);
					} else {
						return super.getChildPullReader();
					}
				}
				@Override
				protected void onEndTag() throws XmlParseException {
					if ( parentItem == null ) {
						list.addItem(item);
					} else {
						parentItem.addChildItem(item);
					}
				}
			}
		}

		@Override
		public void onEndTag() throws XmlParseException {
			Survey survey = list.getSurvey();
			survey.addCodeList(list);
			this.list = null;
		}
	}
}