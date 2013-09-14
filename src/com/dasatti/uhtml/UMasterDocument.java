package com.dasatti.uhtml;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.undo.*;

import com.Ostermiller.Syntax.*;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import com.inet.jortho.*;

public class UMasterDocument extends JInternalFrame implements InternalFrameListener
{
	JTabbedPane tbpDocumentPane;
	//JPanel m_decPreviewPanel;
	//JEditorPane m_currentDocument;
	//DefaultStyledDocument m_currentStyledDoc;

        JTextPane tpDesignDocument;JScrollPane scDesign;
	JTextPane tpSourceDocument;JScrollPane scSource;
	JTextPane tpPreviewDocument;JScrollPane scPreview;
	
	public MutableAttributeSet attr;		
//	HTMLDocument document; //old
	public HighlightedDocument sourceDocument;
//        public HTMLDocument source_document;
	//public HTMLEditorKit editorKit; //old

	ExtendedHTMLDocument htmlDocument;
	ExtendedHTMLEditorKit editorKit;
	
	private int uniqueName;
	public int selStart;
	public int selEnd;
	public int carretPos;
	File currentFile=null;
	boolean isUpdated=false;
	boolean isList=false;
	UndoManager undoManager;
	
	UMasterUI parentUI;
	//UMasterDesignDocument m_docDesignPanel;
	//UMasterHTMLDocument m_docHTMLPanel;
	//UMasterPreviewDocument m_docPreviewPanel;


	UMasterDocument()
	{
		super("Untitled Document",true,true,true,true);

		
		tbpDocumentPane=new JTabbedPane(JTabbedPane.BOTTOM);
		
	//	m_currentDocument=new JEditorPane();
	//	m_currentStyledDoc=new DefaultStyledDocument();
		sourceDocument = new HighlightedDocument();
		sourceDocument.setHighlightStyle(HighlightedDocument.HTML_KEY_STYLE);
//		source_document = new HTMLDocument();
                
		tpDesignDocument=new JTextPane();tpDesignDocument.setContentType("text/html; charset=UTF-32");scDesign=new JScrollPane(tpDesignDocument);
		tpSourceDocument=new JTextPane(sourceDocument);tpSourceDocument.setContentType("text/plain; charset=UTF-32");scSource=new JScrollPane(tpSourceDocument);
		tpPreviewDocument=new JTextPane();tpPreviewDocument.setContentType("text/html; charset=UTF-32");scPreview=new JScrollPane(tpPreviewDocument);tpPreviewDocument.setEditable(false);
		
		attr=new SimpleAttributeSet();
//		editorKit = new HTMLEditorKit(); //old
//		document = (HTMLDocument)editorKit.createDefaultDocument(); //old
                editorKit = new ExtendedHTMLEditorKit();
                htmlDocument = (ExtendedHTMLDocument)(editorKit.createDefaultDocument());
		tpDesignDocument.setDocument(htmlDocument);
		//m_htmlDocument.setDocument(source_document);
		//m_htmlDocument.setForeground(Color.blue);
		tpSourceDocument.setFont(new Font("Courier New",0,14));
		SpellChecker.setUserDictionaryProvider( new FileUserDictionary() );
        
        // Load the configuration from the file dictionaries.cnf and 
        // use the current locale or the first language as default 
        try{
        SpellChecker.registerDictionaries(null, "en"  );
        }
        catch(Exception ex)
        {
        	System.out.println(ex);
        }

        // enable the spell checking on the text component with all features
        SpellChecker.register( tpDesignDocument );
		//m_htmlDocument.setDocument(document);
		//m_previewDocument.setDocument(document);
		//m_designDocument.setCharacterAttributes(attr,true);
//		m_htmlDocument.setCharacterAttributes(attr,true);
		//m_previewDocument.setCharacterAttributes(attr,true);
		
		//m_docDesignPanel = new UMasterDesignDocument();
		//m_docHTMLPanel = new UMasterHTMLDocument();
		//m_docPreviewPanel = new UMasterPreviewDocument();
		
		//m_tpane_documentPane.addTab("Design",m_docDesignPanel);
		tbpDocumentPane.addTab("Design",scDesign);
		//m_tpane_documentPane.addTab("HTML",m_docHTMLPanel);
		tbpDocumentPane.addTab("HTML",scSource);
		//m_tpane_documentPane.addTab("Preview",m_docPreviewPanel);
		tbpDocumentPane.addTab("Preview",scPreview);
		
		/////////////Tab Pane Action Listeners
		//m_docDesignPanel.m_designDocument.addFocusListener(new fl_design());
		//m_docHTMLPanel.m_htmlDocument.addFocusListener(new fl_html());
		//m_docPreviewPanel.m_previewDocument.addFocusListener(new fl_preview());
		tpDesignDocument.addKeyListener(
			new KeyAdapter()
			{
				public void keyPressed(KeyEvent ke)
				{
					if(ke.getKeyCode()==KeyEvent.VK_ENTER)
					{
						try
						{
							parentUI._enterKeyListener();
						}
						catch(Exception e)
						{}
					}
				}
			}
			);
                tpDesignDocument.addCaretListener(new CaretListener() {

                    public void caretUpdate(CaretEvent e) {
//                        if(m_designDocument.getSelectionStart()-m_designDocument.getSelectionEnd()!=0)
//                        {
                            // Something is selected
                            parentUI._updateMenus();
//                        }
                    }
                });
		tpDesignDocument.addFocusListener(new fl_design());
		tpDesignDocument.addMouseListener(new mListener());
		tpSourceDocument.addFocusListener(new fl_html());
		tpSourceDocument.addMouseListener(new mListener());
		tpPreviewDocument.addFocusListener(new fl_preview());
		htmlDocument.addDocumentListener(new UpdateListener());
		undoManager=new UndoManager();
		htmlDocument.addUndoableEditListener(new Undoer());
		//m_htmlDocument.addDocumentListener(new UpdateListener());
		//m_previewDocument.addDocumentListener(new UpdateListener());
		
		tpDesignDocument.requestFocus();
		getContentPane().add(tbpDocumentPane);
		pack();
		addInternalFrameListener(this);
		
		try{
		this.setMaximum(true);
		}catch(Exception ex){setSize(780,430);}
		setVisible(true);
		
	}
	
	/*****************************
	PUBLIC METHODS
	*****************************/
	
	public void setUniqueName(int _unique)
	{
		uniqueName=_unique;
	}
	
	public int getUniqueName()
	{
		return uniqueName;
	}
	
	public void setParentUI(UMasterUI _pUI)
	{
		parentUI=_pUI;
	}
	
	public JTextPane getDesignDocument()
	{
		return tpDesignDocument;
	}
	
	public void setCurrentFile(File _f)
	{
		currentFile=_f;
	}
	
	public File getCurrentFile()
	{
		return currentFile;
	}
	
	public void setUpdated(boolean _isUpdated)
	{
		isUpdated=_isUpdated;
	}
	public boolean isUpdated()
	{
		return isUpdated;
	}
	public void setIsList(boolean _isList)
	{
		isList=_isList;
	}
	public boolean isList()
	{
		return isList;
	}
	public void setParentUIInternatDocument()
	{
		parentUI._setCurrentInternalDocument(this);
	}
	
	public Element getElementByTag(HTML.Tag tag) 
	{
		Element root = htmlDocument.getDefaultRootElement();
		return getElementByTag(root, tag);
	}
	
	public Element getElementByTag(Element parent, HTML.Tag tag) 
	{
		if (parent == null || tag == null)
		return null;
		for (int k=0; k<parent.getElementCount(); k++) 
		{
			Element child = parent.getElement(k);
			if (child.getAttributes().getAttribute(StyleConstants.NameAttribute).equals(tag))
			return child;
			Element e = getElementByTag(child, tag);
			if (e != null)
			return e;
		}
		return null;
	}
	
	public void showSpellCheckerDialog()
	{
		SpellChecker.showSpellCheckerDialog(tpDesignDocument,new SpellCheckerOptions());
	}
	
	public void enableAutoSpellCheck(boolean _enable)
	{
		SpellChecker.enableAutoSpell(tpDesignDocument, _enable );
	}
	
	public JMenu getCheckerMenu()
	{
		return SpellChecker.createCheckerMenu(new SpellCheckerOptions());
	}
/*	
	public String getTitle() 
	{
		return (String)document.getProperty(Document.TitleProperty);
	}
	public void setTitle(String title) 
	{
		Dictionary di = document.getDocumentProperties();
		di.put(Document.TitleProperty, title);
		document.setDocumentProperties(di);
	}
*/	
	//this.addFocusListener(new FocusListener()
	//{
	public void internalFrameActivated(InternalFrameEvent ie)
	{
		parentUI._setCurrentInternalDocument(this);
	}
	public void internalFrameClosed(InternalFrameEvent ie)
	{
		//m_parentUI._closeDocument();
		//m_parentUI._setCurrentInternalDocument(null);
	}

	public void internalFrameClosing(InternalFrameEvent ie)
	{
		//System.out.println("Closing");
		parentUI._setCurrentInternalDocument(this);
		boolean b=parentUI._closeDocument();
	}

	public void internalFrameDeactivated(InternalFrameEvent ie)
	{
		;
	}

	public void internalFrameDeiconified(InternalFrameEvent ie)
	{
	}

	public void internalFrameIconified(InternalFrameEvent ie)
	{
		parentUI._setCurrentInternalDocument(this);
	}

	public void internalFrameOpened(InternalFrameEvent ie)
	{
		//m_parentUI._setCurrentInternalDocument(this);
	}

	public void showPopup(Component c, int x, int y)
	{
		parentUI._showPopup(c,x,y);
	}
	
	public void showSourcePopup(Component c, int x, int y)
	{
		parentUI._showSourcePopup(c,x,y);
	}

        public ExtendedHTMLDocument getExtendedHTMLDocument()
        {
            return htmlDocument;
        }

        public ExtendedHTMLEditorKit getExtendedHTMLEditorKit()
        {
            return editorKit;
        }
	//}
	//);
	
	/*public void _insertTag(String intag, String outtag)
	{
		int current_position =m_docDesignPanel.m_designDocument.getCaretPosition();
		//System.out.println("Current pos: "+current_position);
		String selected = m_docDesignPanel.m_designDocument.getSelectedText();
		//if (selected == null) 
		//{
		//		m_docDesignPanel.m_designDocument.select(current_position, current_position);
		//		selected = "";
		//}
		int selection_start = m_docDesignPanel.m_designDocument.getSelectionStart();
		int selection_end = m_docDesignPanel.m_designDocument.getSelectionEnd();
		//System.out.println("Selection start: "+selection_start);
		//System.out.println("Selection end: "+selection_end);
		//m_docDesignPanel.m_designDocument.replaceSelection(intag + selected + outtag);
		JEditorPane new_pane=new JEditorPane();
		new_pane.setContentType("text/plain");
		new_pane.setText(m_docDesignPanel.m_designDocument.getText());
		new_pane.select(selection_start,selection_end);
		if (selected == null) 
		{
				m_docDesignPanel.m_designDocument.select(current_position, current_position);
				selected = "";
		}
		//new_pane.replaceSelection(intag+selected+outtag);
		m_docDesignPanel.m_designDocument.setText(new_pane.getText());
		m_currentDocument.setText(new_pane.getText());
	}
	*/

	
	/*****************************
	INTERNAL CLASSES
	*****************************/
	public class fl_design implements FocusListener
	{
			public void focusGained(FocusEvent fe)
			{
				//m_docDesignPanel.m_designDocument.setText(m_currentDocument.getText());
				//m_docDesignPanel.m_designDocument.requestFocus();
				setParentUIInternatDocument();
                                parentUI._updateSaveButtons();
                                tpDesignDocument.select(selStart, selEnd);
			}
			public void focusLost(FocusEvent fe)
			{
				//m_currentDocument.setText(m_docDesignPanel.m_designDocument.getText());
				selStart=tpDesignDocument.getSelectionStart();
				selEnd=tpDesignDocument.getSelectionEnd();
				carretPos=tpDesignDocument.getCaretPosition();
				tpSourceDocument.setText(tpDesignDocument.getText());
				tpPreviewDocument.setText(tpSourceDocument.getText());
				
			}
	};
	public class fl_html implements FocusListener
	{
			public void focusGained(FocusEvent fe)
			{
				//m_docHTMLPanel.m_htmlDocument.setText(m_currentDocument.getText());
				//m_docHTMLPanel.m_htmlDocument.requestFocus();
				setParentUIInternatDocument();
                                parentUI._updateSaveButtons();
			}
			public void focusLost(FocusEvent fe)
			{
				//m_currentDocument.setText(m_docHTMLPanel.m_htmlDocument.getText());
				tpDesignDocument.setText(tpSourceDocument.getText());
				tpPreviewDocument.setText(tpSourceDocument.getText());
			}
	};
	public class fl_preview implements FocusListener
	{
			public void focusGained(FocusEvent fe)
			{
				//m_docPreviewPanel.m_previewDocument.setText(m_currentDocument.getText());
				//m_docPreviewPanel.m_previewDocument.requestFocus();
				setParentUIInternatDocument();
                                parentUI._updateSaveButtons();
				
			}
			public void focusLost(FocusEvent fe)
			{
				
			}
	};
	public class UpdateListener implements DocumentListener
	{
			public void insertUpdate(DocumentEvent e)
			{
				isUpdated=true;
				parentUI._updateUndo();
                                parentUI._updateSaveButtons();
			}
			public void removeUpdate(DocumentEvent e)
			{
				isUpdated=true;
				parentUI._updateUndo();
                                parentUI._updateSaveButtons();
			}
			public void changedUpdate(DocumentEvent e)
			{
				isUpdated=true;
				parentUI._updateUndo();
                                parentUI._updateSaveButtons();
			}
	}
	
	public class mListener implements MouseListener
	{
		public void mouseReleased(MouseEvent e)
		{
			if(e.isPopupTrigger() && e.getSource()==tpDesignDocument)
			{
				showPopup((Component)e.getSource(),e.getPoint().x,e.getPoint().y);
			}
			else
			if(e.isPopupTrigger() && e.getSource()==tpSourceDocument)
			{
				showSourcePopup((Component)e.getSource(),e.getPoint().x,e.getPoint().y);
			}
		}
		public void mousePressed(MouseEvent e)
		{
			///if(e.isPopupTrigger())
			//{
				//showPopup((Component)e.getSource(),e.getPoint().x,e.getPoint().y);
			//}
		}
		public void mouseExited(MouseEvent e)
		{
		}
		public void mouseEntered(MouseEvent e)
		{
		}
		public void mouseClicked(MouseEvent e)
		{
		}
	}
	
	/**************************************************
	UNDOWER CLASS
	*/
	class Undoer implements UndoableEditListener 
	{
		public Undoer() 
		{
			undoManager.die();
			//updateUndo();
		}
		public void undoableEditHappened(UndoableEditEvent e) 
		{
			UndoableEdit edit = e.getEdit();
			undoManager.addEdit(e.getEdit());
			//updateUndo();
		}
	}


}
