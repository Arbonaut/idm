package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class CodeListsPR extends IdmlPullReader {
	
	public CodeListsPR() {
		super("codeLists", 1);
		addChildPullReaders(new CodeListPR());
	}

	private class CodeListPR extends IdmlPullReader {
		
		private CodeList list;
		
		public CodeListPR() {
			super("list");
			addChildPullReaders(new LabelPR(), new CodingSchemePR(), new HierarchyPR(), new DescriptionPR(), new ItemsPR());
		}
		
		@Override
		protected boolean onStartTag() throws XmlParseException {
			int id = getIntegerAttribute("id", true);
			String name = getAttribute("name", false);
			String lookupTable = getAttribute("lookup", false);
			Survey survey = getSurvey();
			list = survey.createCodeList(id);
			list.setName(name);
			list.setLookupTable(lookupTable);
			return false;
		}

		private class CodingSchemePR extends IdmlPullReader {
			public CodingSchemePR() {
				super("codingScheme", 1);
			}
			
			@Override
			protected boolean onStartTag()
					throws XmlParseException, XmlPullParserException, IOException {
				String scopeStr = getAttribute("scope", true);
				try {
					CodeScope scope = CodeList.CodeScope.valueOf(scopeStr.toUpperCase());
					list.setCodeScope(scope);
					return false;
				} catch ( IllegalArgumentException ex ) {
					throw new XmlParseException(getParser(), "invalid scope "+scopeStr);
				}
			}
		}
		
		private class HierarchyPR extends IdmlPullReader {
			public HierarchyPR() {
				super("hierarchy", 1);
				addChildPullReaders(new LevelPR());
			}
			
			private class LevelPR extends IdmlPullReader {
				private CodeListLevel level;
				
				public LevelPR() {
					super("level");
					addChildPullReaders(new LabelPR(), new DescriptionPR());						
				}
				
				@Override
				protected boolean onStartTag()
						throws XmlParseException, XmlPullParserException, IOException {
					this.level = new CodeListLevel();
					String name = getAttribute("name", true);
					level.setName(name);
					return false;
				}

				private class LabelPR extends LanguageSpecificTextPR {
					public LabelPR() {
						super("label");
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						level.addLabel(lst);
					}
				}

				private class DescriptionPR extends LanguageSpecificTextPR {
					public DescriptionPR() {
						super("description");
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
				super("label", true);
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
				super("description");
			}
			
			@Override
			protected void processText(LanguageSpecificText lst) {
				list.addDescription(lst);
			}
		}

		private class ItemsPR extends IdmlPullReader {
			
			public ItemsPR() {
				super("items", 1);
				addChildPullReaders(new ItemPR());
			}
			
			private class ItemPR extends IdmlPullReader {
				private CodeListItem parentItem;
				private CodeListItem item;
				
				public ItemPR() {
					super("item");
					addChildPullReaders(new CodePR(), new LabelPR(), new DescriptionPR());
				} 
				
				public ItemPR(CodeListItem parentItem) {
					this();
					this.parentItem = parentItem;
				}

				@Override
				protected boolean onStartTag()
						throws XmlParseException, XmlPullParserException, IOException {
					int id = getIntegerAttribute("id", true);
					Boolean q = getBooleanAttribute("qualifiable", false);
					this.item = list.createItem(id);
					item.setQualifiable(q==null ? false : q);
					return false;
				}
				
				private class CodePR extends TextPullReader {
					public CodePR() {
						super("code", 1);
					}
					
					@Override
					protected void processText(String text) {
						item.setCode(text);
					}
				}

				private class LabelPR extends LanguageSpecificTextPR {
					public LabelPR() {
						super("label");
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						item.addLabel(lst);
					}
				}

				private class DescriptionPR extends LanguageSpecificTextPR {
					public DescriptionPR() {
						super("description");
					}
					
					@Override
					protected void processText(LanguageSpecificText lst) {
						item.addDescription(lst);
					}
				}

				@Override
				protected XmlPullReader getChildTagReader() throws XmlParseException {
					XmlPullParser parser = getParser();
					String name = parser.getName();
					if ( name.equals("item") ) {
						return new ItemPR(item);
					} else {
						return super.getChildTagReader();
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