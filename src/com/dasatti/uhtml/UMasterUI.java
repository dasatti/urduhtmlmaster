package com.dasatti.uhtml;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.tree.*;

import com.lang.LangControler;
import java.nio.charset.Charset;
//import com.gui.toolbar.UMasterFileToolBar;
//import com.gui.toolbar.UMasterFormatToolBar;


public class UMasterUI extends JPanel
{
	
	JDesktopPane m_dPane;
	JPanel m_topPanel;
	JPanel m_centerPanel;
	JPanel m_centerLeftPanel;
	JPanel m_centerRightPanel;
	JPanel m_bottomPanel;
	
	JSplitPane m_splitPane,m_splitPane2;
	//JTabbedPane m_tp_ToolBar;
	
	UMasterFrame m_parentFrame;
	UMasterMenu m_mnu_MenuBar;
	UMasterFileToolBar m_tb_FileTollBar;
	UMasterFormatToolBar m_tb_FormatTollBar;
	UMasterHTMLToolBar m_tb_HTMLTollBar;
	UMasterProjectPanel m_pnl_ProjectPanel;
	UMasterPropertiesPane m_jtp_PropertiesPane;
	UMasterStatusMenu m_pnl_StatusBar;
	UMasterPopMenu m_popMenu;
	UMasterPopMenu m_source_popMenu;
	LangControler objLang;
	TableDlg m_tableDlg;
	
	UMasterDocument m_internalFrame_document;
        
        static UMasterUI masterUI;
	
	String m_btn_path="./res/images/buttons/";
	private int m_num_document=0;
	private int m_num_tdocuments=0;
	ArrayList m_alNames,m_alUniqueNames,m_alFrames;
	
	UMasterUI()
	{
		setLayout(new BorderLayout());
		
		m_alNames=new ArrayList();
		m_alUniqueNames=new ArrayList();
		m_alFrames=new ArrayList();
		
		
		
		/**********************
		TOP PANEL
		**********************/
		m_topPanel=new JPanel();
		//m_tp_ToolBar=new JTabbedPane();
		m_mnu_MenuBar=new UMasterMenu();
		m_tb_FileTollBar=new UMasterFileToolBar();
		m_tb_FormatTollBar=new UMasterFormatToolBar();
		m_tb_HTMLTollBar=new UMasterHTMLToolBar();
		m_pnl_ProjectPanel=new UMasterProjectPanel();
		m_jtp_PropertiesPane=new UMasterPropertiesPane();
		m_pnl_StatusBar=new UMasterStatusMenu();
		m_tb_FormatTollBar.setMasterUI(this);
		m_tb_HTMLTollBar.setMasterUI(this);
		BorderLayout top_layout=new BorderLayout();
		//top_layout.setAlignment(BorderLayout.LEFT);
		m_topPanel.setLayout(top_layout);
		//m_tp_ToolBar.addTab("File",m_tb_FileTollBar);
		//m_tp_ToolBar.addTab("Format",m_tb_FormatTollBar);
		//m_topPanel.add(m_tp_ToolBar);
		
		m_topPanel.add(m_tb_FileTollBar,BorderLayout.NORTH);
		m_topPanel.add(m_tb_FormatTollBar,BorderLayout.CENTER);
		m_topPanel.add(m_tb_HTMLTollBar,BorderLayout.SOUTH);
		add(m_topPanel, BorderLayout.NORTH);
		
		/**********************
		LEFT PANEL
		**********************/
		//add(new JLabel("Left Panel"), BorderLayout.WEST);
		
		/**********************
		RIGHT PANEL
		**********************/
		//add(new JLabel("Right Panel"), BorderLayout.EAST);
		
		/**********************
		CENTER PANEL
		**********************/
		m_centerPanel=new JPanel();
		m_centerLeftPanel=new JPanel();
		m_centerRightPanel=new JPanel();
		m_bottomPanel=new JPanel();
		m_centerLeftPanel.setLayout(new BorderLayout());
		m_centerRightPanel.setLayout(new BorderLayout());
		m_bottomPanel.setLayout(new BorderLayout());
		m_dPane=new JDesktopPane();
		//m_internalFrame_document=new UMasterDocument();
		//m_internalFrame_document.setParentUI(this);
		//m_dPane.add(m_internalFrame_document);
		_newDocument();
                
		m_centerRightPanel.add(m_dPane,BorderLayout.CENTER);
		m_centerLeftPanel.setMaximumSize(new Dimension(200,200));
		m_centerLeftPanel.add(m_pnl_ProjectPanel,BorderLayout.CENTER);
		m_centerRightPanel.setMinimumSize(new Dimension(200,200));
		m_bottomPanel.add(m_jtp_PropertiesPane,BorderLayout.CENTER);
		m_bottomPanel.setMaximumSize(new Dimension(200,200));
		m_splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,m_centerLeftPanel,m_centerRightPanel);
		m_splitPane.setContinuousLayout(true); 
 		m_splitPane.setOneTouchExpandable(true); 
 		m_splitPane.setDividerLocation(200);
 		m_splitPane2=new JSplitPane(JSplitPane.VERTICAL_SPLIT,m_splitPane,m_bottomPanel);
 		m_splitPane2.setDividerLocation(550);
		//m_centerPanel.add(m_dPane);
		_showHidePropertiesPanel(false);
		add(m_splitPane2, BorderLayout.CENTER);
		
		/**********************
		BOTTOM PANEL
		**********************/
		add(m_pnl_StatusBar, BorderLayout.SOUTH);
		
		/**********************
		POPUP MENU
		**********************/
		m_popMenu=new UMasterPopMenu();
		m_source_popMenu=new UMasterPopMenu();
		m_source_popMenu.pmi_Hyperlink.setVisible(false);
		m_source_popMenu.pmi_Image.setVisible(false);
		m_source_popMenu.pmi_Font.setVisible(false);
		
		
		objLang=new LangControler();
		_addKeymaps();
		//_setKeymap("Urdu");
		m_internalFrame_document.tpDesignDocument.grabFocus();
                
                masterUI = this;
		
	}
	
	/************************
	METHODS DEFINATIONS
	************************/
	
        public static UMasterUI getMasterUI()
        {
            return masterUI;
        }
        
	public void _setParentFrame(UMasterFrame _pFrame)
	{
		m_parentFrame=_pFrame;
	}
	
	public JMenuBar _getMenuBar()
	{
		return m_mnu_MenuBar;
	}
	
	public void _setCurrentInternalDocument(UMasterDocument _m_internalFrame_document)
	{
		m_internalFrame_document=_m_internalFrame_document;
	}
	
	public Element _getElementByTag(HTML.Tag tag) 
	{
		return m_internalFrame_document.getElementByTag(tag);
	}
	
	public Element _getElementByTag(Element parent, HTML.Tag tag) 
	{
		return m_internalFrame_document.getElementByTag(parent,tag );
	}
	public UMasterDocument _setCurrentInternalDocument()
	{
		return m_internalFrame_document;
	}
	
	public void _showHideFileBar(boolean _show)
	{
		if(_show)
			m_tb_FileTollBar.setVisible(true);
		else
			m_tb_FileTollBar.setVisible(false);
			
	}
	public void _showHideFormatBar(boolean _show)
	{
		if(_show)
			m_tb_FormatTollBar.setVisible(true);
		else
			m_tb_FormatTollBar.setVisible(false);
			
	}
	public void _showHideHTMLBar(boolean _show)
	{
		if(_show)
			m_tb_HTMLTollBar.setVisible(true);
		else
			m_tb_HTMLTollBar.setVisible(false);
			
	}
	
	public void _showHidePropertiesPanel(boolean _show)
	{
		if(_show)
		{
			//m_jtp_PropertiesPane.setVisible(true);
			m_bottomPanel.setVisible(true);
			m_splitPane2.setDividerLocation(550);
		}
		else
		{
		 	//m_jtp_PropertiesPane.setVisible(false);
		 	m_bottomPanel.setVisible(false);
		}
			
	}
	
	public void _showHideProjectPanel(boolean _show)
	{
		if(_show)
		{
			//m_jtp_PropertiesPane.setVisible(true);
			m_centerLeftPanel.setVisible(true);
			m_splitPane.setDividerLocation(200);
		}
		else
		{
		 	//m_jtp_PropertiesPane.setVisible(false);
		 	m_centerLeftPanel.setVisible(false);
		}
			
	}
	
	public void _showPopup(Component _c, int _x, int _y)
	{
		m_popMenu.show(_c,_x,_y);
	}
	
	public void _showSourcePopup(Component _c, int _x, int _y)
	{
		m_source_popMenu.show(_c,_x,_y);
	}
	
	public void _find()
	{
//		FindDialog dlg=new FindDialog((HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument(),0,m_internalFrame_document.getDesignDocument());
                new FindDialog(this,0);
	}
	
	public int _find(String searchKey,int searchIndex)
	{
		if(searchKey.length()==0)
		{
			JOptionPane.showMessageDialog(this,"Please specify text you want to find");
			return 0;
		}
		else
		{
			try
			{
				String AllText=m_internalFrame_document.getDesignDocument().getText(0,m_internalFrame_document.getDesignDocument().getDocument().getLength());
				String searchFrom="",key="";
				int startIndex=-1;
				int endIndex=-1;
				
				searchFrom=AllText.toLowerCase();
				key=searchKey.toLowerCase();
				
				
				startIndex=searchFrom.indexOf(key,searchIndex);
				if(!(endIndex>=m_internalFrame_document.getDesignDocument().getDocument().getLength()-1))
				{
					endIndex=startIndex+key.length();
					searchIndex=endIndex;
				}
			
				if(startIndex<0)
				{
					JOptionPane.showMessageDialog(this,"Input Text not found");
					return 0;
				}
				else
				{
					//ed_Pane.doc.setSelection(startIndex,endIndex,true);
					//JOptionPane.showMessageDialog(this,"Input Text found between index "+startIndex+" and "+endIndex);
					m_internalFrame_document.getDesignDocument().select(startIndex,endIndex);
					m_internalFrame_document.getDesignDocument().grabFocus();
					return endIndex;
				}
			}
			catch(BadLocationException e)
			{
				JOptionPane.showMessageDialog(this,"Please specify text you want to find");
				return 0;
			}
		}
	}
	
	public void _replace()
	{
//		FindDialog dlg=new FindDialog((HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument(),1,m_internalFrame_document.getDesignDocument());
            new FindDialog(this,1);
	}

        public void _select(int _startIndex, int _endIndex)
        {
            if(_startIndex!=_endIndex && _startIndex>=0)
            {
                m_internalFrame_document.getDesignDocument().grabFocus();
                m_internalFrame_document.getDesignDocument().select(_startIndex, _endIndex);
            }

        }

	public void _setFontFamily(String _fName)
	{

                StyledEditorKit kit =(StyledEditorKit) m_internalFrame_document.getDesignDocument().getEditorKit();
		MutableAttributeSet inp_attr = kit.getInputAttributes();
		int xStart=m_internalFrame_document.getDesignDocument().getSelectionStart();
		int xEnd=m_internalFrame_document.getDesignDocument().getSelectionEnd();
		int cPos=m_internalFrame_document.getDesignDocument().getCaretPosition();
		MutableAttributeSet sas = new SimpleAttributeSet();							
		StyleConstants.setFontFamily(sas, _fName);
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
		doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
                inp_attr.addAttributes(sas);
		m_internalFrame_document.tpDesignDocument.grabFocus();
		
	}
	
	public void _setFontSize(int _fSize)
	{

                StyledEditorKit kit =(StyledEditorKit) m_internalFrame_document.getDesignDocument().getEditorKit();
		MutableAttributeSet inp_attr = kit.getInputAttributes();
                int xStart=m_internalFrame_document.getDesignDocument().getSelectionStart();
                int xEnd=m_internalFrame_document.getDesignDocument().getSelectionEnd();
		int cPos=m_internalFrame_document.getDesignDocument().getCaretPosition();
		MutableAttributeSet sas = new SimpleAttributeSet();
//                AttributeSet sas = m_internalFrame_document.getDesignDocument().getCharacterAttributes();
		StyleConstants.setFontSize(sas,_fSize);
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
                if(xStart!= xEnd)
                    doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
                inp_attr.addAttributes(sas);
		m_internalFrame_document.tpDesignDocument.grabFocus();

	}
	
	public void _setForeColor()
	{
                 StyledEditorKit kit =(StyledEditorKit) m_internalFrame_document.getDesignDocument().getEditorKit();
		MutableAttributeSet inp_attr = kit.getInputAttributes();
		Color fColor=JColorChooser.showDialog(m_internalFrame_document.tpDesignDocument,"Chosoe Font Color",Color.blue);
		int xStart=m_internalFrame_document.selStart;
		int xEnd=m_internalFrame_document.selEnd;
		int cPos=m_internalFrame_document.carretPos;
		MutableAttributeSet sas = new SimpleAttributeSet();							
		StyleConstants.setForeground(sas, fColor);
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
		doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
                inp_attr.addAttributes(sas);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	public void _setBackColor()
	{
                StyledEditorKit kit =(StyledEditorKit) m_internalFrame_document.getDesignDocument().getEditorKit();
		MutableAttributeSet inp_attr = kit.getInputAttributes();
		Color fColor=JColorChooser.showDialog(m_internalFrame_document.tpDesignDocument,"Chosoe Font Color",Color.blue);
		int xStart=m_internalFrame_document.selStart;
		int xEnd=m_internalFrame_document.selEnd;
		int cPos=m_internalFrame_document.carretPos;
		MutableAttributeSet sas = new SimpleAttributeSet();							
		StyleConstants.setBackground(sas, fColor);
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
		doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
                inp_attr.addAttributes(sas);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	public void _insertImage()
	{
		JFileChooser jfc=new JFileChooser();
		if(jfc.showOpenDialog(m_internalFrame_document.tpDesignDocument)==JFileChooser.APPROVE_OPTION)
		{
		
			File f=jfc.getSelectedFile();
			try
			{
//				String url=f.toURL().toString();
                                String url=f.getAbsolutePath();
                                
				//JOptionPane.showMessageDialog(m_internalFrame_document.m_designDocument,url,,"URL"JOptionPane.INFORMATION_MESSAGE);
				if(url!=null)
				{
					ImageIcon img=new ImageIcon(new URL(url));
					int w=img.getIconWidth();
					int h=img.getIconHeight();
					if(w<=0 || h<=0)
					{
						JOptionPane.showMessageDialog(m_internalFrame_document.tpDesignDocument,"Error opening Image","Error Occured",JOptionPane.ERROR_MESSAGE);
						
					}
					else
					{
						MutableAttributeSet attr = new SimpleAttributeSet();
						attr.addAttribute(StyleConstants.NameAttribute,HTML.Tag.IMG);
						attr.addAttribute(HTML.Attribute.SRC, url);
						attr.addAttribute(HTML.Attribute.HEIGHT,Integer.toString(h));
						attr.addAttribute(HTML.Attribute.WIDTH,Integer.toString(w));
						int p = m_internalFrame_document.tpDesignDocument.getCaretPosition();
						m_internalFrame_document.tpDesignDocument.getDocument().insertString(p, " ", attr);
					}
					
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(m_internalFrame_document.tpDesignDocument,"Error","Error retrievng image",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void _insertImage2()	throws IOException, BadLocationException, RuntimeException
	{
		ImageDialog dlgImg=new ImageDialog(null);
		dlgImg.show();
		
		if(dlgImg.succeeded())
		{
//			String insertHTML=dlgImg.getHTML();
//			//System.out.println(insertHTML);
//			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
//			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.document, caretPos, insertHTML, 0, 0, HTML.Tag.IMG);
//			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
//			m_internalFrame_document.m_designDocument.grabFocus();
                    
                    String imgUrl = dlgImg.getImagePath();
                    _insertImage(imgUrl);
		}
	}
	
        public void _insertImage(String _image)
        {
//            try {
//                // Get the text pane's document
//                StyledDocument doc = (StyledDocument)
//                        m_internalFrame_document.m_designDocument.getDocument();
//
//                // The image must first be wrapped in a style
//                Style style = doc.addStyle("StyleName", null);
//                StyleConstants.setIcon(style, new ImageIcon(_image));
//
//                // Insert the image at the end of the text
//                doc.insertString(doc.getLength(), "", style);
                /*
//                int xStart=m_internalFrame_document.m_sStart;
//		int xEnd=m_internalFrame_document.m_sEnd;
//		int cPos=m_internalFrame_document.m_carretPos;
//		MutableAttributeSet sas = new SimpleAttributeSet();							
//		StyleConstants.setIcon(sas, new ImageIcon(_image));
//		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
//		doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
//		m_internalFrame_document.m_designDocument.grabFocus();
                 * */
            
            /*
            m_internalFrame_document.m_designDocument.insertIcon(new ImageIcon(_image));
             * */
//            } catch (BadLocationException e) {
//            }

            
            ImageIcon image1 = new ImageIcon(_image);
            //m_internalFrame_document.tpDesignDocument.insertIcon(image1);
            //System.out.println("add image icon---");

            int w = image1.getIconWidth();
            int h = image1.getIconHeight();
            MutableAttributeSet mas = new SimpleAttributeSet();
            mas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.IMG);
            mas.addAttribute(HTML.Attribute.SRC,"file:"+ _image);
            mas.addAttribute(HTML.Attribute.HEIGHT, Integer.toString(h));
            mas.addAttribute(HTML.Attribute.WIDTH, Integer.toString(w));

            try
            {
            int p = m_internalFrame_document.tpDesignDocument.getCaretPosition();
            m_internalFrame_document.tpDesignDocument.getDocument().insertString(p, " ", mas);
            //m_internalFrame_document.getExtendedHTMLEditorKit().insertHTML(m_internalFrame_document.getExtendedHTMLDocument(), p, "<IMG SRC=\"file:" + _image + "\">", 0, 0, HTML.Tag.IMG);
            }
            catch(Exception e)
            {
            }

        }
	public boolean _saveFile(boolean isSaveAs)
	{
		File f;
		boolean ownFile=false;
		if(m_internalFrame_document.getCurrentFile()==null || isSaveAs)
		{
			JFileChooser jfc=new JFileChooser();
			if(jfc.showSaveDialog(m_internalFrame_document.tpDesignDocument)!=JFileChooser.APPROVE_OPTION)
				return false;
			f=jfc.getSelectedFile();
			if(f==null)
				return false;
		}
		else
		{
			f=m_internalFrame_document.getCurrentFile();
			ownFile=true;
		}
		//this.setCurser(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(f.exists() && !ownFile)
		{
			int r=JOptionPane.showConfirmDialog(m_internalFrame_document.tpDesignDocument,"The file already exists! Do you want to overwrite it?","File alreay exists",JOptionPane.YES_NO_OPTION);
			if(r==1)
				return true;
		}
		try
		{
			OutputStream os=new FileOutputStream(f);
                       OutputStreamWriter osr = new OutputStreamWriter(os,Charset.forName("UTF8"));
                       String text = m_internalFrame_document.tpDesignDocument.getDocument().getText(0, m_internalFrame_document.tpDesignDocument.getDocument().getLength());
                       osr.write(text);
//                       System.out.println(text);
                       // BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF8"));

                       // System.out.println(m_internalFrame_document.m_designDocument.getDocument().getText(0, m_internalFrame_document.m_designDocument.getDocument().getLength()));
			m_internalFrame_document.editorKit.write(os,m_internalFrame_document.tpDesignDocument.getDocument(),0,m_internalFrame_document.tpDesignDocument.getDocument().getLength());
			os.close();
		}
		catch(Exception ex)
		{
			return false;
		}
		//this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		m_internalFrame_document.setCurrentFile(f);
		m_internalFrame_document.setTitle(f.getName());
                m_internalFrame_document.setUpdated(false);
                _updateSaveButtons();
		return true;
	}
	
	public void _trackNewDocuments(UMasterDocument _frameDocument)
	{
		String _title=_frameDocument.getTitle();
		m_alNames.add(_title);
		m_alUniqueNames.add(m_num_tdocuments);
		m_alFrames.add(_frameDocument);
		m_internalFrame_document.setUniqueName(m_num_tdocuments);
		m_pnl_ProjectPanel.addHTMLFile(_title,m_num_tdocuments);
	}
	
	public void _trackCloseDocuments(int _unique)
	{
		int index=m_alUniqueNames.indexOf(_unique);
		if(index>=0)
		{
			try
			{
				m_alNames.remove(index);
				m_alUniqueNames.remove(index);
				m_alFrames.remove(index);
				m_pnl_ProjectPanel.removeHTMLFile(_unique);
			}
			catch(Exception e)
			{}
		}
	}
	
	public void _newDocument()
	{
		UMasterDocument m_internalFrame_document2=new UMasterDocument();
		m_dPane.add(m_internalFrame_document2);
		m_internalFrame_document=m_internalFrame_document2;
		m_internalFrame_document.setParentUI(this);
		_addKeymaps();
		m_num_document++;
		m_num_tdocuments++;
		
		//m_pnl_ProjectPanel.tnodeHTML.add(new DefaultMutableTreeNode("Untitled Document"+m_num_document));
		String title="Untitled Document"+m_num_document;
		m_internalFrame_document.setTitle(title);
		_trackNewDocuments(m_internalFrame_document2);
		m_internalFrame_document.moveToFront();
		try{
		m_internalFrame_document.setMaximum(true);
		}catch(Exception ex){}
		m_internalFrame_document.grabFocus();
                _updateSaveButtons();
                _updateUndo();
		//validate();
	}
	
	public void _newDocument(HTMLDocument _doc)
	{
		UMasterDocument m_internalFrame_document2=new UMasterDocument();
		m_internalFrame_document2.tpDesignDocument.setDocument(_doc);
		m_dPane.add(m_internalFrame_document2);
		m_internalFrame_document=m_internalFrame_document2;
		m_internalFrame_document.setParentUI(this);
		_addKeymaps();
		m_num_document++;
		m_num_tdocuments++;
		m_internalFrame_document.moveToFront();
		try{
		m_internalFrame_document.setMaximum(true);
		}catch(Exception ex){}
		m_internalFrame_document.grabFocus();
                _updateSaveButtons();
                _updateUndo();
		//validate();
	}
	
	public void _newDocument(HTMLDocument _doc,String _title)
	{
		_newDocument(_doc);
		m_internalFrame_document.setTitle(_title);
		_trackNewDocuments(m_internalFrame_document);
	}
	
	public void _openDocument()
	{
		JFileChooser jfc=new JFileChooser();
		if(jfc.showOpenDialog(m_internalFrame_document.tpDesignDocument)!=JFileChooser.APPROVE_OPTION)
				return ;
		File f=jfc.getSelectedFile();
		if(f==null || !f.isFile())
			return;
		
		
		try
		{
			InputStream in=new FileInputStream(f);
			HTMLEditorKit m_kit=new HTMLEditorKit();
			HTMLDocument m_doc=(HTMLDocument)m_internalFrame_document.editorKit.createDefaultDocument();
			m_kit.read(in,m_doc,0);
			StyleSheet m_context=m_doc.getStyleSheet();
			_newDocument(m_doc,f.getName());
			m_internalFrame_document.setCurrentFile(f);
			in.close();
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not open the file","Error Opening File",JOptionPane.ERROR_MESSAGE);
		}
	}
	
        public void _openDocument(File f)
	{
		if(f==null || !f.isFile() || (!f.getName().endsWith("html") && !f.getName().endsWith("htm")))
                {
                    JOptionPane.showMessageDialog(null, "Invalid file specified","Could not Open File",JOptionPane.ERROR_MESSAGE);
			return;
                }
		
		try
		{
			InputStream in=new FileInputStream(f);
			HTMLEditorKit m_kit=new HTMLEditorKit();
			HTMLDocument m_doc=(HTMLDocument)m_internalFrame_document.editorKit.createDefaultDocument();
			m_kit.read(in,m_doc,0);
			StyleSheet m_context=m_doc.getStyleSheet();
			_newDocument(m_doc,f.getName());
			m_internalFrame_document.setCurrentFile(f);
			in.close();
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not open the file","Error Opening File",JOptionPane.ERROR_MESSAGE);
		}
	}
        
	public boolean _closeDocument()
	{
		if(_promptForSave())
		{
			if(m_internalFrame_document!=null)
			{
				m_internalFrame_document.setUpdated(false);
                                _updateSaveButtons();
				//m_pnl_ProjectPanel.removeHTMLFile(m_internalFrame_document.getUniqueName());
				_trackCloseDocuments(m_internalFrame_document.getUniqueName());
				m_internalFrame_document.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m_internalFrame_document.dispose();
			}
			return true;
		}
		else
		{
			m_internalFrame_document.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			return false;
		}
	}
	
	public boolean _closeAllDocuments()
	{
		if(_closeDocument())
		{
			Iterator itrFrame=m_alFrames.iterator();
			try
			{
				while(itrFrame.hasNext())
				{
					_setCurrentInternalDocument((UMasterDocument)itrFrame.next());
					//UMasterDocument nFrame=(UMasterDocument)itrFrame.next();
					//nFrame.setClosed(true);
					//if(!_closeDocument())
					//	return false;
					_closeAllDocuments();
				}
				
			}
			catch(Exception e){  }	
			return true;
		}
		return false;
	}
	
	public boolean _promptForSave()
	{
		if(!m_internalFrame_document.isUpdated())
			return true;
		else
		{
			int result=JOptionPane.showConfirmDialog(m_internalFrame_document.tpPreviewDocument,"Do you want to save the changes before closing?","Document Not Saved",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
			
			switch(result)
			{
				case JOptionPane.YES_OPTION:
					if(!_saveFile(false))
						return false;
					else
					{	
						m_internalFrame_document.setUpdated(false);
						return true;
					}
				case JOptionPane.NO_OPTION:
					return true;
				case JOptionPane.CANCEL_OPTION:
					return false;
				
				//default:
				//	return true;
			}
			return true;
		}
	}
	
	public void _exit()
	{
		if(_promptForSave())
		{
			System.exit(0);
		}
	}
	
	public boolean _canUndo()
	{
		return m_internalFrame_document.undoManager.canUndo();
	}
	
	public boolean _canRedo()
	{
		return m_internalFrame_document.undoManager.canRedo();
	}
	
	public void _undo()
	{
		try
		{
			m_internalFrame_document.undoManager.undo();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public void _redo()
	{
		try
		{
			m_internalFrame_document.undoManager.redo();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	protected void _updateUndo() 
	{
		if(_canUndo()) 
		{
			m_tb_FileTollBar.tbBtnUndo.setEnabled(true);
			m_mnu_MenuBar.mi_Undo.setEnabled(true);
			//m_undoAction.putValue(Action.NAME,m_undo.getUndoPresentationName());
		}
		
		else 
		{
			m_tb_FileTollBar.tbBtnUndo.setEnabled(false);
			m_mnu_MenuBar.mi_Undo.setEnabled(false);
			//m_undoAction.putValue(Action.NAME, "Undo");
		}
		if(_canRedo()) 
		{
			m_tb_FileTollBar.tbBtnRedo.setEnabled(true);
			m_mnu_MenuBar.mi_Redo.setEnabled(true);
			//m_redoAction.putValue(Action.NAME,m_undo.getRedoPresentationName());
		}
		else 
		{
			m_tb_FileTollBar.tbBtnRedo.setEnabled(false);
			m_mnu_MenuBar.mi_Redo.setEnabled(false);
			//m_redoAction.putValue(Action.NAME, "Redo");
		}
	}
	
	private void _setDocumentProperties()
	{
		DocumentPropsDialog dlg=new DocumentPropsDialog(null,(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument());
		dlg.show();
		if(dlg.succeeded())
		{
			MutableAttributeSet as=dlg.getAttributes();
			//Element body = _getElementByTag(HTML.Tag.BODY);
			//MutableAttributeSet mattr=new SimpleAttributeSet();
			//try
		//	{
		//	mattr =(SimpleAttributeSet)body.getAttributes();
	//		mattr.addAttributes(as);
	//		}catch(Exception ex){System.out.println(ex);}
		//	doc.setCharacterAttributes(0,doc.getLength(),mattr,false);
			//m_internalFrame_document.getDesignDocument().getInputAttributes().addAttributes(as);
			m_internalFrame_document.getDesignDocument().setParagraphAttributes(as,false);
			HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
			m_internalFrame_document.getDesignDocument().setDocument(new HTMLDocument());
			m_internalFrame_document.getDesignDocument().setDocument(doc);
			m_internalFrame_document.getDesignDocument().revalidate();
			m_internalFrame_document.getDesignDocument().repaint();
		}
	}
	
	private void _setHeading(HTML.Tag _h)
	{
		int xStart=m_internalFrame_document.selStart;
		int xEnd=m_internalFrame_document.selEnd;
		int cPos=m_internalFrame_document.carretPos;
		MutableAttributeSet sas = new SimpleAttributeSet();	
		sas.addAttribute(StyleConstants.NameAttribute,_h);						
		//StyleConstants.setFontSize(sas,_fSize);
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
		//if(xStart!=xEnd)
			doc.setParagraphAttributes(xStart,xEnd-xStart,sas,false);
		//else
		//{
		//	MutableAttributeSet inputAttributes =m_internalFrame_document.editorKit.getInputAttributes();
		//	inputAttributes.addAttributes(sas);
		//}
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	private void _setFont()
	{
		FontDialog dlg = new FontDialog(null);
		AttributeSet a = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.tpDesignDocument.getCaretPosition()).getAttributes();
		dlg.setAttributes(a);
		dlg.show();
		if (dlg.succeeded()) 
		{
			//setAttributeSet(dlg.getAttributes());
			int xStart=m_internalFrame_document.selStart;
			int xEnd=m_internalFrame_document.selEnd;
			int cPos=m_internalFrame_document.carretPos;
			HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
			m_internalFrame_document.tpDesignDocument.grabFocus();
			doc.setCharacterAttributes(xStart,xEnd-xStart,dlg.getAttributes(),false);
			//showAttributes(m_internalFrame_document.m_designDocument.getCaretPosition());
		}
	}
	
	private void _OL()
	{
		
		int cPos=m_internalFrame_document.getDesignDocument().getCaretPosition();
		
		if(m_internalFrame_document.isList())
			m_internalFrame_document.setIsList(false);
		else
			m_internalFrame_document.setIsList(true);
		try
		{
			
			HTMLDocument htmlDoc = (HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
			String selTextBase = m_internalFrame_document.getDesignDocument().getSelectedText();
			int textLength = -1;
			if(selTextBase != null)
			{
				textLength = selTextBase.length();
			}

			if(selTextBase == null || textLength < 1)
			{
				StringBuffer sbNew = new StringBuffer();
				
				sbNew.append("<OL><li> </li></OL>");
				m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, m_internalFrame_document.getDesignDocument().getCaretPosition(), sbNew.toString(), 0, 0, HTML.Tag.OL);
				m_internalFrame_document.getDesignDocument().setCaretPosition(cPos + 1);
				m_internalFrame_document.tpDesignDocument.grabFocus();
			}
			else
			{
			int iStart = m_internalFrame_document.getDesignDocument().getSelectionStart();
			int iEnd   = m_internalFrame_document.getDesignDocument().getSelectionEnd();
			String selText = htmlDoc.getText(iStart, iEnd - iStart);
			StringBuffer sbNew = new StringBuffer();
			String sToken = ((selText.indexOf("\r") > -1) ? "\r" : "\n");
			StringTokenizer stTokenizer = new StringTokenizer(selText, sToken);
			sbNew.append("<OL>");
			while(stTokenizer.hasMoreTokens())
			{
				sbNew.append("<LI>");
				sbNew.append(stTokenizer.nextToken());
				sbNew.append("</LI>");
			}
			sbNew.append("</OL>");

			htmlDoc.remove(iStart, iEnd - iStart);
		 	m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument,iStart,sbNew.toString(),1,1,null);
		 	m_internalFrame_document.getDesignDocument().select(iStart,iEnd+1);
		 	}
		 	//editor.setText(editor.getText());
			m_internalFrame_document.getDesignDocument().grabFocus();
        }
        catch (Exception e)
        {
        } 
	}
	
	private void _UL()
	{
		
		int cPos=m_internalFrame_document.getDesignDocument().getCaretPosition();
		
		if(m_internalFrame_document.isList())
			m_internalFrame_document.setIsList(false);
		else
			m_internalFrame_document.setIsList(true);
		try
		{
			
			HTMLDocument htmlDoc = (HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
			String selTextBase = m_internalFrame_document.getDesignDocument().getSelectedText();
			int textLength = -1;
			if(selTextBase != null)
			{
				textLength = selTextBase.length();
			}

			if(selTextBase == null || textLength < 1)
			{
				StringBuffer sbNew = new StringBuffer();
				
				sbNew.append("<UL><li> </li></UL>");
				m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, m_internalFrame_document.getDesignDocument().getCaretPosition(), sbNew.toString(), 0, 0, HTML.Tag.UL);
				m_internalFrame_document.getDesignDocument().setCaretPosition(cPos + 1);
				m_internalFrame_document.tpDesignDocument.grabFocus();
			}
			else
			{
			int iStart = m_internalFrame_document.getDesignDocument().getSelectionStart();
			int iEnd   = m_internalFrame_document.getDesignDocument().getSelectionEnd();
			String selText = htmlDoc.getText(iStart, iEnd - iStart);
			StringBuffer sbNew = new StringBuffer();
			String sToken = ((selText.indexOf("\r") > -1) ? "\r" : "\n");
			StringTokenizer stTokenizer = new StringTokenizer(selText, sToken);
			sbNew.append("<UL>");
			while(stTokenizer.hasMoreTokens())
			{
				sbNew.append("<LI>");
				sbNew.append(stTokenizer.nextToken());
				sbNew.append("</LI>");
			}
			sbNew.append("</UL>");

			htmlDoc.remove(iStart, iEnd - iStart);
		 	m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument,iStart,sbNew.toString(),1,1,null);
		 	m_internalFrame_document.getDesignDocument().select(iStart,iEnd+1);
		 	}
		 	//editor.setText(editor.getText());
			m_internalFrame_document.getDesignDocument().grabFocus();
        }
        catch (Exception e)
        {
        } 
	}
	
	protected String _hyperlinkDlg(String prompt, String initialValue) 
	{
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(new JLabel(prompt));
		p.add(Box.createHorizontalGlue());
		JButton bt = new JButton("Local File");
		bt.setRequestFocusEnabled(false);
		p.add(bt);
		final JOptionPane op = new JOptionPane(p,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		op.setWantsInput(true);
		if (initialValue != null)
		op.setInitialSelectionValue(initialValue);
		ActionListener lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(null) !=JFileChooser.APPROVE_OPTION)
					return;
				File f = chooser.getSelectedFile();
				try 
				{
					String str = f.toURL().toString();
					op.setInitialSelectionValue(str);
				}
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		};
		bt.addActionListener(lst);
		JDialog dlg = op.createDialog(this, "Urdu HTML Master");
		dlg.show();
		dlg.dispose();
		Object value = op.getInputValue();
		if (value == JOptionPane.UNINITIALIZED_VALUE)
			return null;
		String str = (String)value;
		if (str != null && str.length() == 0)
			str = null;
		return str;
	}
	
	public void _insertHyperlink()
	throws IOException, BadLocationException, RuntimeException
	{
		String oldHref = null;
		int p = m_internalFrame_document.getDesignDocument().getCaretPosition();
		AttributeSet attr = m_internalFrame_document.htmlDocument.getCharacterElement(p).getAttributes();
		AttributeSet anchor =(AttributeSet)attr.getAttribute(HTML.Tag.A);
		if (anchor != null)
			oldHref = (String)anchor.getAttribute(HTML.Attribute.HREF);
		String newHref = _hyperlinkDlg("Please enter link URL:", oldHref);
		if (newHref == null)
			return;
		SimpleAttributeSet attr2 = new SimpleAttributeSet();
		attr2.addAttribute(StyleConstants.NameAttribute, HTML.Tag.A);
		attr2.addAttribute(HTML.Attribute.HREF, newHref);
		//setAttributeSet(attr2, true);
		int xStart=m_internalFrame_document.selStart;
		int xEnd=m_internalFrame_document.selEnd;
		int cPos=m_internalFrame_document.carretPos;
		int sLen=xEnd-xStart;
		String sText=m_internalFrame_document.getDesignDocument().getSelectedText();
		HTMLDocument doc=(HTMLDocument)m_internalFrame_document.getDesignDocument().getDocument();
		doc.setCharacterAttributes(xStart,xEnd-xStart,attr2,false);
		//m_internalFrame_document.getDesignDocument().delete(xStart,sLen);
		doc.remove(xStart,sLen);
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, cPos, "<a href=\""+newHref+"\">"+sText+"</a>", 0, 0, HTML.Tag.A);
		m_internalFrame_document.getDesignDocument().setCaretPosition(cPos);
		m_internalFrame_document.getDesignDocument().grabFocus();
	}
	
	private void _insertPara()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<P></P>", 1, 0, HTML.Tag.P);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	private void _insertBreak()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<BR>", 0, 0, HTML.Tag.BR);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	private void _insertHR()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<HR>", 0, 0, HTML.Tag.HR);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	public void _insertTable()
	{
		//TableDlg dlg=new TableDlg(m_internalFrame_document.m_previewDocument,m_internalFrame_document.document);
		TableDlg dlg=new TableDlg(null,m_internalFrame_document.htmlDocument);
		dlg.show();
		if(dlg.succeeded())
		{
			String tableHTML=dlg.generateHTML();
			Element ep=m_internalFrame_document.htmlDocument.getParagraphElement(m_internalFrame_document.getDesignDocument().getSelectionStart());
                        String elementParentName = ep.getParentElement().getName();
			try
			{
				//m_internalFrame_document.htmlDocument.insertAfterEnd(ep,tableHTML);
                            ActionEvent actionEvent = new ActionEvent(m_internalFrame_document.getDesignDocument(), 0, "insertTable");
                           new HTMLEditorKit.InsertHTMLTextAction("insertTable", tableHTML, HTML.Tag.BODY, HTML.Tag.TABLE).actionPerformed(actionEvent);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

	
	private void _insertTableRow()
	{
                 /*
                 * This commented does not work on opened files*/
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		Element	element = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.getDesignDocument().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint  = -1;
		int columnCount = -1;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("tr"))
			{
				startPoint  = elementParent.getStartOffset();
				columnCount = elementParent.getElementCount();
				break;
			}
			else
			{
				elementParent = elementParent.getParentElement();
                                //System.out.println("----------->parent elemet name is "+elementParent.getName()+" At "+elementParent.getStartOffset());
			}
		}
		if(startPoint > -1 && columnCount > -1)
		{
			m_internalFrame_document.getDesignDocument().setCaretPosition(startPoint);
	 		StringBuffer sRow = new StringBuffer();
 			sRow.append("<TR>");
 			for(int i = 0; i < columnCount; i++)
 			{
 				sRow.append("<TD></TD>");
 			}
 			sRow.append("</TR>");
 			ActionEvent actionEvent = new ActionEvent(m_internalFrame_document.getDesignDocument(), 0, "insertTableRow");
 			new HTMLEditorKit.InsertHTMLTextAction("insertTableRow", sRow.toString(), HTML.Tag.TABLE, HTML.Tag.TR).actionPerformed(actionEvent);
 			//refreshOnUpdate();
 			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos);
			m_internalFrame_document.tpDesignDocument.grabFocus();
 		}//*/

                //String sRow = "<TR>";
                //sRow+="</TR>";
                //ActionEvent actionEvent = new ActionEvent(m_internalFrame_document.getDesignDocument(), 0, "insertTableRow");
               // new HTMLEditorKit.InsertHTMLTextAction("insertTableRow", sRow, HTML.Tag.TABLE, HTML.Tag.TR).actionPerformed(actionEvent);

	}
	
	private void _insertTableColumn()
	{
		/*
                 * This commented does not work on opened files
                 */
                int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		Element	element = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.getDesignDocument().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int rowCount   = -1;
		int cellOffset =  0;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("table"))
			{
				startPoint = elementParent.getStartOffset();
				rowCount   = elementParent.getElementCount();
				break;
			}
			else if(elementParent.getName().equals("tr"))
			{
				int rowStart = elementParent.getStartOffset();
				int rowCells = elementParent.getElementCount();
				for(int i = 0; i < rowCells; i++)
				{
					Element currentCell = elementParent.getElement(i);
					if(m_internalFrame_document.getDesignDocument().getCaretPosition() >= currentCell.getStartOffset() && m_internalFrame_document.getDesignDocument().getCaretPosition() <= currentCell.getEndOffset())
					{
						cellOffset = i;
					}
				}
				elementParent = elementParent.getParentElement();
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && rowCount > -1)
		{
			m_internalFrame_document.getDesignDocument().setCaretPosition(startPoint);
			String sCell = "<TD></TD>";
			ActionEvent actionEvent = new ActionEvent(m_internalFrame_document.getDesignDocument(), 0, "insertTableCell");
 			for(int i = 0; i < rowCount; i++)
 			{
 				Element row = elementParent.getElement(i);
 				Element whichCell = row.getElement(cellOffset);
 				m_internalFrame_document.getDesignDocument().setCaretPosition(whichCell.getStartOffset());
				new HTMLEditorKit.InsertHTMLTextAction("insertTableCell", sCell, HTML.Tag.TR, HTML.Tag.TD, HTML.Tag.TH, HTML.Tag.TD).actionPerformed(actionEvent);
 			}
 			//refreshOnUpdate();
 			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos);
 		}//*/
           
	}
	
	private void _insertTableCell()
	{
		String sCell = "<TD></TD>";
		ActionEvent actionEvent = new ActionEvent(m_internalFrame_document.getDesignDocument(), 0, "insertTableCell");
		new HTMLEditorKit.InsertHTMLTextAction("insertTableCell", sCell, HTML.Tag.TR, HTML.Tag.TD, HTML.Tag.TH, HTML.Tag.TD).actionPerformed(actionEvent);
		//refreshOnUpdate();
	}

	private void _deleteTableRow()
	throws BadLocationException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		Element	element = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.getDesignDocument().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int endPoint   = -1;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("tr"))
			{
				startPoint = elementParent.getStartOffset();
				endPoint   = elementParent.getEndOffset();
				break;
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && endPoint > startPoint)
		{
			m_internalFrame_document.htmlDocument.remove(startPoint, endPoint - startPoint);
			m_internalFrame_document.getDesignDocument().setDocument(m_internalFrame_document.htmlDocument);
			//registerDocument(m_internalFrame_document.document);
 			//refreshOnUpdate();
 			if(caretPos >= m_internalFrame_document.htmlDocument.getLength())
 			{
 				caretPos = m_internalFrame_document.htmlDocument.getLength() - 1;
 			}
 			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos);
 		}
	}
	
	private void _deleteTableCol()
	throws BadLocationException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		Element	element       = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.getDesignDocument().getCaretPosition());
		Element elementParent = element.getParentElement();
		Element	elementCell   = (Element)null;
		Element	elementRow    = (Element)null;
		Element	elementTable  = (Element)null;
		// Locate the table, row, and cell location of the cursor
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("td"))
			{
				elementCell = elementParent;
			}
			else if(elementParent.getName().equals("tr"))
			{
				elementRow = elementParent;
			}
			else if(elementParent.getName().equals("table"))
			{
				elementTable = elementParent;
			}
			elementParent = elementParent.getParentElement();
		}
		int whichColumn = -1;
		if(elementCell != null && elementRow != null && elementTable != null)
		{
			// Find the column the cursor is in
			int myOffset = 0;
			for(int i = 0; i < elementRow.getElementCount(); i++)
			{
				if(elementCell == elementRow.getElement(i))
				{
					whichColumn = i;
					myOffset = elementCell.getEndOffset();
				}
			}
			if(whichColumn > -1)
			{
				// Iterate through the table rows, deleting cells from the indicated column
				int mycaretPos = caretPos;
				for(int i = 0; i < elementTable.getElementCount(); i++)
				{
					elementRow  = elementTable.getElement(i);
					elementCell = (elementRow.getElementCount() > whichColumn ? elementRow.getElement(whichColumn) : elementRow.getElement(elementRow.getElementCount() - 1));
					int columnCellStart = elementCell.getStartOffset();
					int columnCellEnd   = elementCell.getEndOffset();
					int dif	= columnCellEnd - columnCellStart;
					if(columnCellStart < myOffset)
					{
						mycaretPos = mycaretPos - dif;
						myOffset = myOffset-dif;
					}
					if(whichColumn==0)
					{
						m_internalFrame_document.htmlDocument.remove(columnCellStart, dif);
					}
					else
					{
						m_internalFrame_document.htmlDocument.remove(columnCellStart-1, dif);
					}
				}
				m_internalFrame_document.getDesignDocument().setDocument(m_internalFrame_document.htmlDocument);
				//registerDocument(m_internalFrame_document.document);
	 			//refreshOnUpdate();
	 			if(mycaretPos >= m_internalFrame_document.htmlDocument.getLength())
	 			{
	 				mycaretPos = m_internalFrame_document.htmlDocument.getLength() - 1;
	 			}
	 			if(mycaretPos < 1)
	 			{
	 				mycaretPos =  1;
 				}
	 			m_internalFrame_document.getDesignDocument().setCaretPosition(mycaretPos);
			}
		}
	}
	
	private void _deleteTableCell()
	throws BadLocationException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		Element	element = m_internalFrame_document.htmlDocument.getCharacterElement(m_internalFrame_document.getDesignDocument().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int endPoint   = -1;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("td"))
			{
				startPoint = elementParent.getStartOffset();
				endPoint   = elementParent.getEndOffset();
				break;
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && endPoint > startPoint)
		{
			m_internalFrame_document.htmlDocument.remove(startPoint, endPoint - startPoint);
			m_internalFrame_document.getDesignDocument().setDocument(m_internalFrame_document.htmlDocument);
			//registerDocument(m_internalFrame_document.document);
 			//refreshOnUpdate();
 			if(caretPos >= m_internalFrame_document.htmlDocument.getLength())
 			{
 				caretPos = m_internalFrame_document.htmlDocument.getLength() - 1;
 			}
 			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos);
 		}
	}
	
	public void _insertForm()	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<FORM METHOD=\"post\" action=\"\" style=\"border-width:1\"> <INPUT TYPE=\"SUBMIT\"><INPUT TYPE=\"RESET\"></FORM>", 0, 0, HTML.Tag.FORM);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	public void _insertField(int _tf_type)	throws IOException, BadLocationException, RuntimeException
	{
		TextFieldDialog dlgTF=new TextFieldDialog(null);
		dlgTF.setTFType(_tf_type);
		dlgTF.show();
		
		if(dlgTF.succeeded())
		{
			String insertHTML=dlgTF.getHTML();
			//System.out.println(insertHTML);
			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
			//m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.document, caretPos, "<INPUT TYPE=\"text\">", 0, 0, HTML.Tag.INPUT);
			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, insertHTML, 0, 0, HTML.Tag.INPUT);
			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
			m_internalFrame_document.tpDesignDocument.grabFocus();
		}
	}
	
	public void _insertTextField()	throws IOException, BadLocationException, RuntimeException
	{
		_insertField(TextFieldDialog.TF_TEXT);
	}
	
	public void _insertPassField()	throws IOException, BadLocationException, RuntimeException
	{
		_insertField(TextFieldDialog.TF_PASS);
	}
	
	public void _insertHiddenField()	throws IOException, BadLocationException, RuntimeException
	{
		_insertField(TextFieldDialog.TF_HIDDEN);
	}
		
	public void _insertTextArea()	throws IOException, BadLocationException, RuntimeException
	{
		TextAreaDialog dlgTF=new TextAreaDialog(null);
		dlgTF.show();
		
		if(dlgTF.succeeded())
		{
			String insertHTML=dlgTF.getHTML();
			//System.out.println(insertHTML);
			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, insertHTML, 0, 0, HTML.Tag.TEXTAREA);
			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
			m_internalFrame_document.tpDesignDocument.grabFocus();
		}
	}

	public void _insertRadioButton()	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<INPUT TYPE=\"radio\">", 0, 0, HTML.Tag.INPUT);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	public void _insertCheckbox()	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<INPUT TYPE=\"checkbox\">", 0, 0, HTML.Tag.INPUT);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	public void _insertCombobox()	throws IOException, BadLocationException, RuntimeException
	{
		ListBoxDialog dlgTF=new ListBoxDialog(null);
		dlgTF.show();
		
		if(dlgTF.succeeded())
		{
			String insertHTML=dlgTF.getHTML();
			//System.out.println(insertHTML);
			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, insertHTML, 0, 0, HTML.Tag.SELECT);
			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
			m_internalFrame_document.tpDesignDocument.grabFocus();
		}
	}
	public void _insertButton()	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
		m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<INPUT TYPE=\"submit\" VALUE=\"Go..\">", 0, 0, HTML.Tag.INPUT);
		m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
		m_internalFrame_document.tpDesignDocument.grabFocus();
	}
	
	public void _enterKeyListener()	throws IOException, BadLocationException, RuntimeException
	{
		if(m_internalFrame_document.isList())
		{
			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.htmlDocument, caretPos, "<LI> </LI>", 1, 0, HTML.Tag.LI);
			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
			m_internalFrame_document.tpDesignDocument.grabFocus();
		}
		/*else
		{
			int caretPos = m_internalFrame_document.getDesignDocument().getCaretPosition();
			m_internalFrame_document.editorKit.insertHTML(m_internalFrame_document.document, caretPos, "<BR>", 0, 0, HTML.Tag.BR);
			m_internalFrame_document.getDesignDocument().setCaretPosition(caretPos + 1);
			m_internalFrame_document.m_designDocument.grabFocus();
		}*/
	}
	
	public void _showKeyboard()
	{
		KeyboardDialog dlg=new KeyboardDialog(null);
		dlg.show();
	}
	
	public void _addKeymaps()
	{
		Keymap parentKeymap=m_internalFrame_document.getDesignDocument().getKeymap();
		objLang.english=m_internalFrame_document.getDesignDocument().addKeymap("English",parentKeymap);
		objLang.urdu=m_internalFrame_document.getDesignDocument().addKeymap("Urdu",parentKeymap);
		JTextComponent.loadKeymap(objLang.urdu,objLang.urduBindings,objLang.urduActions);
		
	}
		
	public void _setKeymap(String _lang)
	{
		if(_lang== "English")
		{
                    

                    m_tb_FormatTollBar.tbBtnLeftAlign.doClick();
                    m_internalFrame_document.getDesignDocument().setKeymap(objLang.english);
                    m_tb_FileTollBar.tbTfFind.setKeymap(objLang.english);
                   // m_internalFrame_document.tpDesignDocument.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    attr.addAttribute(HTML.Attribute.DIR, "LTR");
                    attr.addAttribute(HTML.Attribute.LANG, "EN");
                    StyledDocument sd =(StyledDocument)m_internalFrame_document.tpDesignDocument.getDocument();
                    int st = m_internalFrame_document.tpDesignDocument.getSelectionStart();
                    int se = m_internalFrame_document.tpDesignDocument.getSelectionEnd();
                    sd.setParagraphAttributes(st,se-st,attr, false);
                    m_internalFrame_document.tpDesignDocument.grabFocus();
                    m_pnl_StatusBar.setStatus("English");
		}
		if(_lang== "Urdu")
		{
                    //JTextComponent.loadKeymap(objLang.urdu,objLang.urduBindings,objLang.urduActions);


                    if(!m_tb_HTMLTollBar.tbBtnLang.isSelected())
                            m_tb_HTMLTollBar.tbBtnLang.setSelected(true);
                    m_tb_FormatTollBar.tbBtnRightAlign.doClick();
                    _setFontSize(12);
                    m_internalFrame_document.tpDesignDocument.setKeymap(objLang.urdu);
                    m_tb_FileTollBar.tbTfFind.setKeymap(objLang.urdu);
                    m_pnl_StatusBar.setStatus("Urdu");
                    //m_internalFrame_document.tpDesignDocument.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    attr.addAttribute(HTML.Attribute.DIR, "RTL");
                    attr.addAttribute(HTML.Attribute.LANG, "UR");
                    StyledDocument sd =(StyledDocument)m_internalFrame_document.tpDesignDocument.getDocument();
                    int st = m_internalFrame_document.tpDesignDocument.getSelectionStart();
                    int se = m_internalFrame_document.tpDesignDocument.getSelectionEnd();
                    sd.setParagraphAttributes(st,se-st,attr, false);
                    //m_internalFrame_document.tpDesignDocument.setCharacterAttributes(attr, false);

                    m_internalFrame_document.tpDesignDocument.grabFocus();
		}
		
	}
	
	public void _showSpellCheckerDialog()
	{
		m_internalFrame_document.showSpellCheckerDialog();
	}
	
	public void _enableAutoSpellCheck(boolean _enable)
	{
		m_internalFrame_document.enableAutoSpellCheck(_enable);
	}
	public JMenu _getCheckerMenu()
	{
		return m_internalFrame_document.getCheckerMenu();
	}
	
	public boolean _showInBrowser()
	{
		if(m_internalFrame_document==null)
		{
			JOptionPane.showMessageDialog(m_internalFrame_document.tpDesignDocument,"Please select the document you want to view in browser","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		
		File f=m_internalFrame_document.getCurrentFile();
		if(f==null)
		{
			JOptionPane.showMessageDialog(m_internalFrame_document.tpDesignDocument,"Please save the document before you can view it in browser","Error",JOptionPane.ERROR_MESSAGE);
			if(!_saveFile(false))
				return false;
			else
				_showInBrowser();
		}
		String url=f.getAbsolutePath();
    	String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try
        {
	        if (os.indexOf( "win" ) >= 0) 
	        {
	        	// this doesn't support showing urls in the form of "page.html#nameLink" 
	            rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
	            //rt.exec( "start iexplorer " + url);
	        } else if (os.indexOf( "mac" ) >= 0) {
	            rt.exec( "open " + url);
	        } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
	        	// Do a best guess on unix until we get a platform independent way
	        	// Build a list of browsers to try, in this order.
	        	String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
	        			"netscape","opera","links","lynx"};
	        	
	        	// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
	        	StringBuffer cmd = new StringBuffer();
	        	for (int i=0; i<browsers.length; i++)
	        		cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
	        	
	        	rt.exec(new String[] { "sh", "-c", cmd.toString() });
	        } else {
	        	return false;
	        }
        }catch (IOException e){
        	return false;
        }
        return true;
    }
	
	public void _setDefaultLookAndFeel()
	{
		m_parentFrame.setDefaultLookAndFeel();
	}
	
	public void _setWindowLookAndFeel()
	{
		//m_parentFrame.setWindowsLookAndFeel();
		try
		{
			
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){}
	}
	
	public void _showAboutDialog()
	{
		new AboutDialog(null);
	}

        public void _updateMenus()
        {
            int xStart=m_internalFrame_document.getDesignDocument().getSelectionStart();
            int xEnd=m_internalFrame_document.getDesignDocument().getSelectionEnd();
            int cPos=m_internalFrame_document.getDesignDocument().getCaretPosition();
            MutableAttributeSet sas = (MutableAttributeSet) m_internalFrame_document.getDesignDocument().getCharacterAttributes();
            //StyledEditorKit kit =(StyledEditorKit) m_internalFrame_document.getDesignDocument().getEditorKit();
            //MutableAttributeSet sas = kit.getInputAttributes();
            int size = StyleConstants.getFontSize(sas);
            String fontFamily =  StyleConstants.getFontFamily(sas);
            int alignment = StyleConstants.getAlignment(sas);
            boolean isBold = StyleConstants.isBold(sas);
            boolean isItalic = StyleConstants.isItalic(sas);
            boolean isUnderline = StyleConstants.isUnderline(sas);
            boolean isStrikethrough = StyleConstants.isStrikeThrough(sas);
            boolean isSubscript = StyleConstants.isSubscript(sas);
            boolean isSuperscript = StyleConstants.isSuperscript(sas);
            
            if(xStart==xEnd)
            {
                m_tb_FormatTollBar.tbBtnFont.setEnabled(false);
                m_tb_FormatTollBar.tbBtnFColor.setEnabled(false);
                m_tb_FormatTollBar.tbBtnBgColor.setEnabled(false);

                
                m_tb_FormatTollBar.tbCmbFontSize.setEnabled(false);
                m_tb_FormatTollBar.tbCmbFonts.setEnabled(false);
                if(isBold)
                {
                    m_tb_FormatTollBar.tbBtnBold.setSelected(true);
//                    m_mnu_MenuBar.mi_bold.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnBold.setSelected(false);
//                    m_mnu_MenuBar.mi_bold.setSelected(false);
                }
                if(isItalic)
                {
                    m_tb_FormatTollBar.tbBtnItalic.setSelected(true);
//                    m_mnu_MenuBar.mi_italic.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnItalic.setSelected(false);
//                    m_mnu_MenuBar.mi_italic.setSelected(false);
                }
                if(isUnderline)
                {
                    m_tb_FormatTollBar.tbBtnUnderline.setSelected(true);
//                    m_mnu_MenuBar.mi_underline.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnUnderline.setSelected(false);
//                    m_mnu_MenuBar.mi_underline.setSelected(true);
                }
                if(isStrikethrough)
                {
                    m_tb_FormatTollBar.tbBtnStrike.setSelected(true);
//                    m_mnu_MenuBar.mi_strikethrough.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnStrike.setSelected(false);
//                    m_mnu_MenuBar.mi_strikethrough.setSelected(false);
                }
                if(isSubscript)
                {
                    m_tb_FormatTollBar.tbBtnSubscript.setSelected(true);
//                    m_mnu_MenuBar.mi_subscript.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnSubscript.setSelected(false);
//                    m_mnu_MenuBar.mi_subscript.setSelected(false);
                }
                if(isSuperscript)
                {
                    m_tb_FormatTollBar.tbBtnSuperscript.setSelected(true);
//                    m_mnu_MenuBar.mi_superscript.setSelected(true);
                }
                else
                {
                    m_tb_FormatTollBar.tbBtnSuperscript.setSelected(false);
//                    m_mnu_MenuBar.mi_superscript.setSelected(false);
                }

//                m_tb_FormatTollBar.tbBtnBold.setEnabled(false);
//                m_tb_FormatTollBar.tbBtnItalic.setEnabled(false);
//                m_tb_FormatTollBar.tbBtnUnderline.setEnabled(false);
                m_tb_FormatTollBar.tbBtnStrike.setEnabled(false);
                m_tb_FormatTollBar.tbBtnSubscript.setEnabled(false);
                m_tb_FormatTollBar.tbBtnSuperscript.setEnabled(false);

                m_tb_FileTollBar.tbBtnCopy.setEnabled(false);
                m_tb_FileTollBar.tbBtnCut.setEnabled(false);
                
                m_mnu_MenuBar.mi_Copy.setEnabled(false);
                m_mnu_MenuBar.mi_Cut.setEnabled(false);

                m_tb_HTMLTollBar.tbBtnHyperlink.setEnabled(false);

                m_popMenu.pmi_Copy.setEnabled(false);
                m_popMenu.pmi_Cut.setEnabled(false);
                m_popMenu.pmi_Font.setEnabled(false);
                m_popMenu.pmi_Hyperlink.setEnabled(false);
            }
            else
            {
                m_tb_FormatTollBar.tbCmbFontSize.setEnabled(true);
                m_tb_FormatTollBar.tbCmbFonts.setEnabled(true);


                m_tb_FormatTollBar.tbBtnFont.setEnabled(true);
                m_tb_FormatTollBar.tbBtnFColor.setEnabled(true);
                m_tb_FormatTollBar.tbBtnBgColor.setEnabled(true);

//                m_tb_FormatTollBar.tbBtnBold.setEnabled(true);
//                m_tb_FormatTollBar.tbBtnItalic.setEnabled(true);
//                m_tb_FormatTollBar.tbBtnUnderline.setEnabled(true);
                m_tb_FormatTollBar.tbBtnStrike.setEnabled(true);
                m_tb_FormatTollBar.tbBtnSubscript.setEnabled(true);
                m_tb_FormatTollBar.tbBtnSuperscript.setEnabled(true);

                m_tb_FileTollBar.tbBtnCopy.setEnabled(true);
                m_tb_FileTollBar.tbBtnCut.setEnabled(true);

//                m_mnu_MenuBar.mi_strikethrough.setSelected(false);
//                m_mnu_MenuBar.mi_subscript.setSelected(false);
//                m_mnu_MenuBar.mi_superscript.setSelected(false);

                m_mnu_MenuBar.mi_Copy.setEnabled(true);
                m_mnu_MenuBar.mi_Cut.setEnabled(true);

                m_tb_HTMLTollBar.tbBtnHyperlink.setEnabled(true);
                
                m_popMenu.pmi_Copy.setEnabled(true);
                m_popMenu.pmi_Cut.setEnabled(true);
                m_popMenu.pmi_Font.setEnabled(true);
                m_popMenu.pmi_Hyperlink.setEnabled(true);
            }

            if(m_internalFrame_document.tpSourceDocument.getSelectionStart()==m_internalFrame_document.tpSourceDocument.getSelectionEnd())
            {
                m_source_popMenu.pmi_Copy.setEnabled(false);
                m_source_popMenu.pmi_Cut.setEnabled(false);
            }
            else
            {
                m_source_popMenu.pmi_Copy.setEnabled(true);
                m_source_popMenu.pmi_Cut.setEnabled(true);
            }

            if(alignment==StyleConstants.ALIGN_LEFT)
            {
                m_tb_FormatTollBar.tbBtnLeftAlign.setSelected(true);
                m_tb_FormatTollBar.tbBtnRightAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnCenterAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnJustify.setSelected(false);

//                m_mnu_MenuBar.mi_lAlign.setSelected(true);
//                m_mnu_MenuBar.mi_rAlign.setSelected(false);
//                m_mnu_MenuBar.mi_cAlign.setSelected(false);
//                m_mnu_MenuBar.mi_justify.setSelected(false);
            }
            if(alignment==StyleConstants.ALIGN_RIGHT)
            {
                m_tb_FormatTollBar.tbBtnLeftAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnRightAlign.setSelected(true);
                m_tb_FormatTollBar.tbBtnCenterAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnJustify.setSelected(false);
//                m_mnu_MenuBar.mi_lAlign.setSelected(false);
//                m_mnu_MenuBar.mi_rAlign.setSelected(true);
//                m_mnu_MenuBar.mi_cAlign.setSelected(false);
//                m_mnu_MenuBar.mi_justify.setSelected(false);
            }
            if(alignment==StyleConstants.ALIGN_CENTER)
            {
                m_tb_FormatTollBar.tbBtnLeftAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnRightAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnCenterAlign.setSelected(true);
                m_tb_FormatTollBar.tbBtnJustify.setSelected(false);
//                m_mnu_MenuBar.mi_lAlign.setSelected(false);
//                m_mnu_MenuBar.mi_rAlign.setSelected(true);
//                m_mnu_MenuBar.mi_cAlign.setSelected(false);
//                m_mnu_MenuBar.mi_justify.setSelected(false);
            }
            if(alignment==StyleConstants.ALIGN_JUSTIFIED)
            {
                m_tb_FormatTollBar.tbBtnLeftAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnRightAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnCenterAlign.setSelected(false);
                m_tb_FormatTollBar.tbBtnJustify.setSelected(true);
//                m_mnu_MenuBar.mi_lAlign.setSelected(false);
//                m_mnu_MenuBar.mi_rAlign.setSelected(false);
//                m_mnu_MenuBar.mi_cAlign.setSelected(false);
//                m_mnu_MenuBar.mi_justify.setSelected(true);
            }

        }

        public void _updateSaveButtons()
        {
            if(m_internalFrame_document.isUpdated())
            {
                m_tb_FileTollBar.tbBtnSave.setEnabled(true);
                m_mnu_MenuBar.mi_Save.setEnabled(true);
            }
            else
            {
                m_tb_FileTollBar.tbBtnSave.setEnabled(false);
                m_mnu_MenuBar.mi_Save.setEnabled(false);
            }
        }
	
	/*************************
	ACTION LISTENERS
	**************************/
	
	/*
	public void _bold()
	{
		m_internalFrame_document._bold();
	}
	public void _italic()
	{
		m_internalFrame_document._italic();
	}
	public void _underline()
	{
		m_internalFrame_document._underline();
	}
	*/
	/*
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	*//*
	public void _lalign()
	{
//		StyleConstants.setAlignment(m_internalFrame_document.attr,0);
//		m_internalFrame_document.m_designDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_htmlDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_previewDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
	}
	public void _calign()
	{
//		StyleConstants.setAlignment(m_internalFrame_document.attr,1);
//		m_internalFrame_document.m_designDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_htmlDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_previewDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
	}
	public void _ralign()
	{
//		StyleConstants.setAlignment(m_internalFrame_document.attr,2);
//		m_internalFrame_document.m_designDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_htmlDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
		//m_internalFrame_document.m_previewDocument.setParagraphAttributes(m_internalFrame_document.attr,true);
	}
	*/
	
	
	/**************************************************************************
	                            INNER CLASSES
	***************************************************************************/
	
	/**************************************************
	MENU CLASS
	*/
	public class UMasterMenu extends JMenuBar
	{
		//JMenuBar=new JMenuBar();
		JMenu menu_file;
		JMenu menu_edit;
		JMenu menu_view;
		JMenu menu_insert;
		JMenu menu_format;
		JMenu menu_form;
		JMenu menu_table;
		JMenu menu_Language,menu_LangSelect;
		JMenu menu_Preferences,menu_LookAndFeel;
		JMenu menu_help;
		JMenu mi_tblInsert,mi_tblDelete,mi_frmButtons,mi_headings;
		JMenuItem mi_New,mi_Add,mi_Open,mi_Close,mi_CloseAll,mi_Save,mi_SaveAs,mi_Exit,mi_Cut,mi_Copy,mi_Paste,mi_Undo,mi_Redo,mi_Find,mi_Replace,mi_SelectAll;
		JMenuItem mi_ViewToolbar,mi_ViewFormatbar,mi_ViewHTMLbar,mi_ViewPropertiesPanel,mi_ViewProjectPanel,mi_ViewKeyboard;
		JMenuItem mi_InBreak,mi_InSymbol,mi_InParagraph,mi_InSpace,mi_InHorizontalline,mi_InDate,mi_InPicture,mi_InHyperlink;
		JMenuItem mi_bold,mi_italic,mi_underline,mi_lAlign,mi_cAlign,mi_rAlign,mi_justify,mi_fontProperties,mi_foreground,mi_background,mi_strikethrough,mi_subscript,mi_superscript,mi_h1,mi_h2,mi_h3,mi_h4,mi_h5,mi_h6,mi_pictureProperties,mi_docProperties;
		JMenuItem mi_insertTable,mi_tblInRow,mi_tblInCol,mi_tblInCell,mi_tblDelRow,mi_tblDelCol,mi_tblDelCell,mi_tblSplit,mi_tblMerge,mi_tblProperties;
		JMenuItem mi_frmInsert,mi_frmTField,mi_frmPassField,mi_frmHidden,mi_frmTArea,mi_frmRadio,mi_frmCheck,mi_frmCombo,mi_frmButton,mi_frmBtnSubmit,mi_frmBtnReset,mi_frmBtnImage,mi_frmBtnSimple;
		JMenuItem mi_langUrdu,mi_langEnglish,mi_UMasterLAF,mi_WindowLAF,mi_AutoSpellCheck,mi_About;	
	
		public UMasterMenu()
		{
			menu_file=new JMenu("File");
			menu_file.setMnemonic('F');
			menu_edit=new JMenu("Edit");
			menu_edit.setMnemonic('E');
			menu_view=new JMenu("View");
			menu_view.setMnemonic('V');
			menu_insert=new JMenu("Insert");
			menu_insert.setMnemonic('I');
			menu_format=new JMenu("Format");
			menu_format.setMnemonic('O');
			menu_form=new JMenu("Form");
			menu_form.setMnemonic('R');
			menu_table=new JMenu("Table");
			menu_table.setMnemonic('T');
			menu_Language=new JMenu("Language");
			menu_Language.setMnemonic('L');
			menu_LangSelect=new JMenu("Select");
			menu_Preferences=new JMenu("Preferences");
			menu_Preferences.setMnemonic('P');
			menu_LookAndFeel=new JMenu("Choose Look and Feel");
			menu_help=new JMenu("Help");
			menu_help.setMnemonic('H');
			
			mi_New=new JMenuItem("New",new ImageIcon(m_btn_path+"newdoc.gif"));
			mi_New.setAccelerator(KeyStroke.getKeyStroke('N',Event.CTRL_MASK));
			mi_Add=new JMenuItem("Add Project",new ImageIcon(m_btn_path+"additem.gif"));
			mi_Open=new JMenuItem("Open",new ImageIcon(m_btn_path+"open.gif"));
			mi_Open.setAccelerator(KeyStroke.getKeyStroke('O',Event.CTRL_MASK));
			mi_Close=new JMenuItem("Close",new ImageIcon(m_btn_path+"close.gif"));
			mi_CloseAll=new JMenuItem("Close All",new ImageIcon(m_btn_path+"close.gif"));
			mi_Save=new JMenuItem("Save",new ImageIcon(m_btn_path+"save.gif"));
			mi_Save.setAccelerator(KeyStroke.getKeyStroke('S',Event.CTRL_MASK));
			mi_SaveAs=new JMenuItem("Save As",new ImageIcon(m_btn_path+"saveas.gif"));
			mi_Exit=new JMenuItem("Exit");
			mi_Cut=new JMenuItem("Cut",new ImageIcon(m_btn_path+"cut.gif"));
			mi_Cut.setAccelerator(KeyStroke.getKeyStroke('X',Event.CTRL_MASK));
			mi_Copy=new JMenuItem("Copy",new ImageIcon(m_btn_path+"copy.gif"));
			mi_Copy.setAccelerator(KeyStroke.getKeyStroke('C',Event.CTRL_MASK));
			mi_Paste=new JMenuItem("Paste",new ImageIcon(m_btn_path+"paste.gif"));
			mi_Paste.setAccelerator(KeyStroke.getKeyStroke('V',Event.CTRL_MASK));
			mi_Undo=new JMenuItem("Undo",new ImageIcon(m_btn_path+"undo.gif"));
			mi_Undo.setAccelerator(KeyStroke.getKeyStroke('Z',Event.CTRL_MASK));
			mi_Redo=new JMenuItem("Redo",new ImageIcon(m_btn_path+"redo.gif"));
			mi_Redo.setAccelerator(KeyStroke.getKeyStroke('Y',Event.CTRL_MASK));
			mi_Find=new JMenuItem("Find",new ImageIcon(m_btn_path+"find.gif"));
			mi_Find.setAccelerator(KeyStroke.getKeyStroke('F',Event.CTRL_MASK));
			mi_Replace=new JMenuItem("Replace",new ImageIcon(m_btn_path+"findnext.gif"));
			mi_Replace.setAccelerator(KeyStroke.getKeyStroke('H',Event.CTRL_MASK));
			mi_SelectAll=new JMenuItem("Select All",new ImageIcon(m_btn_path+"selall.gif"));
			mi_SelectAll.setAccelerator(KeyStroke.getKeyStroke('A',Event.CTRL_MASK));
			mi_ViewToolbar=new JCheckBoxMenuItem("Show Toolbar",true);
			mi_ViewFormatbar=new JCheckBoxMenuItem("Show Formatbar",true);
			mi_ViewHTMLbar=new JCheckBoxMenuItem("Show HTMLbar",true);
			mi_ViewPropertiesPanel=new JCheckBoxMenuItem("Show Properties Panel",false);
			mi_ViewProjectPanel=new JCheckBoxMenuItem("Show Project Panel",true);
			mi_ViewKeyboard=new JMenuItem("Show Keyboard");
			mi_ViewKeyboard.setIcon(new ImageIcon(m_btn_path+"keyboard.gif"));
			mi_ViewKeyboard.setAccelerator(KeyStroke.getKeyStroke('K',Event.CTRL_MASK));
			mi_InBreak=new JMenuItem("Insert Break",new ImageIcon(m_btn_path+"br.gif"));
			mi_InSymbol=new JMenuItem("Insert Symbol");
			mi_InParagraph=new JMenuItem("Insert Paragraph",new ImageIcon(m_btn_path+"para.gif"));
			mi_InSpace=new JMenuItem("Insert Space",new ImageIcon(m_btn_path+"nbsp.gif"));
			mi_InHorizontalline=new JMenuItem("Insert Horizontal Rule",new ImageIcon(m_btn_path+"hr.gif"));
			mi_InDate=new JMenuItem("Insert Date");
			mi_InPicture=new JMenuItem("Insert Image",new ImageIcon(m_btn_path+"insert_picture.gif"));
			mi_InHyperlink=new JMenuItem("Insert Link",new ImageIcon(m_btn_path+"insert_hyperlink.gif"));
			mi_bold=new JMenuItem("Bold",new ImageIcon(m_btn_path+"bold.gif"));
			mi_bold.setAccelerator(KeyStroke.getKeyStroke('B',Event.CTRL_MASK));
			mi_italic=new JMenuItem("Italic",new ImageIcon(m_btn_path+"italic.gif"));
			mi_italic.setAccelerator(KeyStroke.getKeyStroke('I',Event.CTRL_MASK));
			mi_underline=new JMenuItem("Underline",new ImageIcon(m_btn_path+"under.gif"));
			mi_underline.setAccelerator(KeyStroke.getKeyStroke('U',Event.CTRL_MASK));
			mi_lAlign=new JMenuItem("Left Align",new ImageIcon(m_btn_path+"left.gif"));
			mi_lAlign.setAccelerator(KeyStroke.getKeyStroke('L',Event.CTRL_MASK));
			mi_rAlign=new JMenuItem("Right Align",new ImageIcon(m_btn_path+"right.gif"));
			mi_rAlign.setAccelerator(KeyStroke.getKeyStroke('R',Event.CTRL_MASK));
			mi_cAlign=new JMenuItem("Center Align",new ImageIcon(m_btn_path+"center.gif"));
			mi_cAlign.setAccelerator(KeyStroke.getKeyStroke('E',Event.CTRL_MASK));
			mi_justify=new JMenuItem("Justify",new ImageIcon(m_btn_path+"justify.gif"));
			mi_justify.setAccelerator(KeyStroke.getKeyStroke('J',Event.CTRL_MASK));
			mi_fontProperties=new JMenuItem("Font Properties",new ImageIcon(m_btn_path+"font.gif"));
			mi_foreground=new JMenuItem("Foreground",new ImageIcon(m_btn_path+"forecolor.gif"));
			mi_background=new JMenuItem("Background",new ImageIcon(m_btn_path+"bgcolor.gif"));
			mi_strikethrough=new JMenuItem("Strikethrough",new ImageIcon(m_btn_path+"strikethrough.gif"));
			mi_subscript=new JMenuItem("Subscript",new ImageIcon(m_btn_path+"subscript.gif"));
			mi_superscript=new JMenuItem("Superscript",new ImageIcon(m_btn_path+"superscript.gif"));
			mi_headings=new JMenu("Headings");
			mi_h1=new JMenuItem("H1",new ImageIcon(m_btn_path+"h1.gif"));
			mi_h2=new JMenuItem("H2",new ImageIcon(m_btn_path+"h2.gif"));
			mi_h3=new JMenuItem("H3",new ImageIcon(m_btn_path+"h3.gif"));
			mi_h4=new JMenuItem("H4",new ImageIcon(m_btn_path+"h4.gif"));
			mi_h5=new JMenuItem("H5",new ImageIcon(m_btn_path+"h5.gif"));
			mi_h6=new JMenuItem("H6",new ImageIcon(m_btn_path+"h6.gif"));
			mi_pictureProperties=new JMenuItem("Picture Properties",new ImageIcon(m_btn_path+"pic_properties.gif"));
			mi_docProperties=new JMenuItem("Document Properties",new ImageIcon(m_btn_path+"doc_properties.gif"));
			
			mi_tblInsert=new JMenu("Insert");
			mi_tblDelete=new JMenu("Delete");
			mi_insertTable=new JMenuItem("Insert Table",new ImageIcon(m_btn_path+"insert_table.gif"));
			mi_tblInRow=new JMenuItem("Insert Row",new ImageIcon(m_btn_path+"insrow.gif"));
			mi_tblInCol=new JMenuItem("Insert Column",new ImageIcon(m_btn_path+"inscol.gif"));
			mi_tblInCell=new JMenuItem("Insert Cell",new ImageIcon(m_btn_path+"inscell.gif"));
			mi_tblDelRow=new JMenuItem("Delete Row",new ImageIcon(m_btn_path+"delrow.gif"));
			mi_tblDelCol=new JMenuItem("Delete Column",new ImageIcon(m_btn_path+"delcol.gif"));
			mi_tblDelCell=new JMenuItem("Delete Cell",new ImageIcon(m_btn_path+"delcell.gif"));
			mi_tblSplit=new JMenuItem("Split Cells");
			mi_tblMerge=new JMenuItem("Merge Cells");
			mi_tblProperties=new JMenuItem("Table Properties",new ImageIcon(m_btn_path+"table_properties.gif"));
			mi_frmInsert=new JMenuItem("Insert Form",new ImageIcon(m_btn_path+"form.gif"));
			mi_frmTField=new JMenuItem("Text Field",new ImageIcon(m_btn_path+"text.gif"));
			mi_frmPassField=new JMenuItem("Password Field",new ImageIcon(m_btn_path+"password.gif"));
			mi_frmHidden=new JMenuItem("Hidden Field",new ImageIcon(m_btn_path+"hidden.gif"));
			mi_frmTArea=new JMenuItem("Text Area",new ImageIcon(m_btn_path+"textarea.gif"));
			mi_frmRadio=new JMenuItem("Radio Button",new ImageIcon(m_btn_path+"radio.gif"));
			mi_frmCheck=new JMenuItem("Checkbox",new ImageIcon(m_btn_path+"checkbox.gif"));
			mi_frmCombo=new JMenuItem("Listbox",new ImageIcon(m_btn_path+"select.gif"));
			mi_frmButton=new JMenuItem("Push Button",new ImageIcon(m_btn_path+"button.gif"));
			mi_frmButtons=new JMenu("Buttons");
			mi_frmBtnSubmit=new JMenuItem("Submit button");
			mi_frmBtnReset=new JMenuItem("Reset Button");
			mi_frmBtnImage=new JMenuItem("Image Button");
			mi_frmBtnSimple=new JMenuItem("Simple Button");
			mi_langUrdu=new JMenuItem("Urdu",new ImageIcon(m_btn_path+"urdu.gif"));
			mi_langEnglish=new JMenuItem("English",new ImageIcon(m_btn_path+"english.gif"));
			mi_UMasterLAF=new JRadioButtonMenuItem("Urdu HTML Master Look And Feel");
			mi_WindowLAF=new JRadioButtonMenuItem("Windows Look And Feel");
			mi_AutoSpellCheck=new JCheckBoxMenuItem("Auto Spell Check On",true);
			mi_About=new JMenuItem("About");
			
			/**************************
			* ACTIONS
			*/
			mi_New.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						_newDocument();
					}
				}
			);
			mi_Add.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
				}
			}
			);
			mi_Open.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_openDocument();
				}
			}
			);
			mi_Close.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						m_internalFrame_document.setClosed(true);
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not close","Please select the window you want to close",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			);
			mi_CloseAll.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						//m_internalFrame_document.setClosed(true);
						_closeAllDocuments();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not close","Please select the window you want to close",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			);
			mi_Save.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					if(!_saveFile(false))
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not save the file","Error Saving File",JOptionPane.ERROR_MESSAGE);
				}
			}
			);
			mi_SaveAs.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					if(!_saveFile(true))
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not save the file","Error Saving File",JOptionPane.ERROR_MESSAGE);
				}
			}
			);
			mi_Exit.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_exit();
				}
			}
			);
			mi_Undo.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_undo();
					_updateUndo();
				}
			}
			);
			mi_Redo.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_redo();
					_updateUndo();
				}
			}
			);
			mi_Cut.addActionListener(new HTMLEditorKit.CutAction());
			mi_Copy.addActionListener(new HTMLEditorKit.CopyAction());
			mi_Paste.addActionListener(new HTMLEditorKit.PasteAction());
			mi_SelectAll.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_internalFrame_document.tpDesignDocument.selectAll();
					}
				}
				);
			mi_Find.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_find();
					}
				}
				);
			mi_Replace.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_replace();
					}
				}
				);
			mi_ViewToolbar.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_ViewToolbar.isSelected())
							_showHideFileBar(true);
						else
							_showHideFileBar(false);
					}
				}
				);
			mi_ViewFormatbar.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_ViewFormatbar.isSelected())
							_showHideFormatBar(true);
						else
							_showHideFormatBar(false);
					}
				}
				);
			mi_ViewHTMLbar.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_ViewHTMLbar.isSelected())
							_showHideHTMLBar(true);
						else
							_showHideHTMLBar(false);
					}
				}
				);
			mi_ViewPropertiesPanel.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_ViewPropertiesPanel.isSelected())
							_showHidePropertiesPanel(true);
						else
							_showHidePropertiesPanel(false);
					}
				}
				);
			mi_ViewProjectPanel.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_ViewProjectPanel.isSelected())
							_showHideProjectPanel(true);
						else
							_showHideProjectPanel(false);
					}
				}
				);
			mi_ViewKeyboard.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_showKeyboard();
					}
				}
				);
						mi_InParagraph.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertPara();
						}catch(Exception ex){}
					}
				}
				);
			mi_InBreak.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertBreak();
						}catch(Exception ex){}
					}
				}
				);
			mi_InHorizontalline.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertHR();
						}
						catch(Exception ex){}
					}
				}
				);
			mi_InPicture.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_insertImage();
					}
				}
				);
			mi_InHyperlink.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertHyperlink();
						}
						catch(Exception ex){}
					}
				}
			);
			mi_bold.addActionListener(
				new HTMLEditorKit.BoldAction()
				);
			mi_italic.addActionListener(
				new HTMLEditorKit.ItalicAction()
				);
			mi_underline.addActionListener(
				new HTMLEditorKit.UnderlineAction()
				);
			mi_lAlign.addActionListener(
				new HTMLEditorKit.AlignmentAction("AlignLeft", StyleConstants.ALIGN_LEFT)
				);
			mi_cAlign.addActionListener(
				new HTMLEditorKit.AlignmentAction("AlignCenter", StyleConstants.ALIGN_CENTER)
				);
			mi_rAlign.addActionListener(
				new HTMLEditorKit.AlignmentAction("AlignRight", StyleConstants.ALIGN_RIGHT)
				);
			mi_justify.addActionListener(
				new HTMLEditorKit.AlignmentAction("AlignJustify", StyleConstants.ALIGN_JUSTIFIED)
				);
			mi_strikethrough.addActionListener(			
			new StrikeThroughAction()
				);
			mi_subscript.addActionListener(
				
				new SubscriptAction()
				);
			mi_superscript.addActionListener(
				
				new SuperscriptAction()
				);
			mi_fontProperties.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setFont();
				}
			}
			);
			mi_foreground.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setForeColor();
				}
			}
			);
			mi_background.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setBackColor();
				}
			}
			);
			mi_h1.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H1);
				}
			}
			);
			mi_h2.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H2);
				}
			}
			);
			mi_h3.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H4);
				}
			}
			);
			mi_h5.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H5);
				}
			}
			);
			mi_h6.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H6);
				}
			}
			);
			mi_h1.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_setHeading(HTML.Tag.H1);
				}
			}
			);
			mi_insertTable.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTable();
				}
			}
			);
			mi_tblInRow.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableRow();
				}
			}
			);
			mi_tblInCol.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableColumn();
				}
			}
			);
			mi_tblInCell.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableCell();
				}
			}
			);
			mi_tblDelRow.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableRow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
			mi_tblDelCol.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableCol();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
			mi_tblDelCell.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableCell();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
			mi_frmInsert.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertForm();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmTField.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertTextField();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmPassField.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertPassField();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmHidden.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertHiddenField();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmTArea.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertTextArea();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmRadio.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertRadioButton();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmCheck.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertCheckbox();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmCombo.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertCombobox();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_frmButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_insertButton();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_docProperties.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							_setDocumentProperties();
						}
						catch(Exception ex)
						{
						}
					}
				}
				);
			mi_langUrdu.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_setKeymap("Urdu");
					}
				}
				);
			mi_langEnglish.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						_setKeymap("English");
					}
				}
				);
			mi_AutoSpellCheck.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_AutoSpellCheck.isSelected())
						{
							_enableAutoSpellCheck(true);
						}
						else
						{
							_enableAutoSpellCheck(false);
						}
					}
				}
				);
			mi_UMasterLAF.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_UMasterLAF.isSelected())
						{
							_setDefaultLookAndFeel();
						}
					}
				}
				);
			mi_WindowLAF.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(mi_WindowLAF.isSelected())
						{
							_setWindowLookAndFeel();
						}
					}
				}
				);
			mi_About.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
							_showAboutDialog();
					}
				}
				);
										
			menu_file.add(mi_New);
			//menu_file.add(mi_Add);
			menu_file.add(mi_Open);
			menu_file.add(mi_Close);
			menu_file.add(mi_CloseAll);
			menu_file.addSeparator();
			menu_file.add(mi_Save);
			menu_file.add(mi_SaveAs);
			menu_file.addSeparator();
			menu_file.add(mi_Exit);
			menu_edit.add(mi_Undo);
			menu_edit.add(mi_Redo);
			menu_edit.addSeparator();
			menu_edit.add(mi_Copy);
			menu_edit.add(mi_Cut);
			menu_edit.add(mi_Paste);
			menu_edit.addSeparator();
			menu_edit.add(mi_SelectAll);
			menu_edit.addSeparator();
			menu_edit.add(mi_Find);
			menu_edit.add(mi_Replace);
			menu_view.add(mi_ViewToolbar);
			menu_view.add(mi_ViewFormatbar);
			menu_view.add(mi_ViewHTMLbar);
			menu_view.add(mi_ViewProjectPanel);
			//menu_view.add(mi_ViewPropertiesPanel);
			menu_insert.add(mi_InBreak);
			//menu_insert.add(mi_InSymbol);
			menu_insert.add(mi_InParagraph);
			menu_insert.add(mi_InSpace);
			menu_insert.add(mi_InHorizontalline);
			//menu_insert.add(mi_InDate);
			menu_insert.addSeparator();
			menu_insert.add(mi_InPicture);
			menu_insert.add(mi_InHyperlink);
			menu_format.add(mi_bold);
                        mi_bold.setSelected(true);
			menu_format.add(mi_italic);
			menu_format.add(mi_underline);
			menu_format.addSeparator();
			menu_format.add(mi_lAlign);
			menu_format.add(mi_rAlign);
			menu_format.add(mi_cAlign);
			menu_format.add(mi_justify);
			menu_format.addSeparator();
			menu_format.add(mi_fontProperties);
			menu_format.add(mi_foreground);
			menu_format.add(mi_background);
			menu_format.addSeparator();
			menu_format.add(mi_strikethrough);
			menu_format.add(mi_subscript);
			menu_format.add(mi_superscript);
			mi_headings.add(mi_h1);
			mi_headings.add(mi_h2);
			mi_headings.add(mi_h3);
			mi_headings.add(mi_h4);
			mi_headings.add(mi_h5);
			mi_headings.add(mi_h6);
			menu_format.add(mi_headings);
			menu_format.addSeparator();
			//menu_format.add(mi_pictureProperties);
			menu_format.add(mi_docProperties);
			menu_table.add(mi_tblInsert);
			mi_tblInsert.add(mi_insertTable);
			mi_tblInsert.add(mi_tblInRow);
			mi_tblInsert.add(mi_tblInCol);
			mi_tblInsert.add(mi_tblInCol);
			menu_table.add(mi_tblDelete);
			mi_tblDelete.add(mi_tblDelRow);
			mi_tblDelete.add(mi_tblDelCol);
			mi_tblDelete.add(mi_tblDelCell);
			//menu_table.addSeparator();
			//menu_table.add(mi_tblSplit);
			//menu_table.add(mi_tblMerge);
			//menu_table.addSeparator();
			//menu_table.add(mi_tblProperties);
			menu_form.add(mi_frmInsert);
			menu_form.add(mi_frmTField);
			menu_form.add(mi_frmPassField);
			menu_form.add(mi_frmHidden);
			menu_form.add(mi_frmTArea);
			menu_form.addSeparator();
			menu_form.add(mi_frmRadio);
			menu_form.add(mi_frmCheck);
			menu_form.add(mi_frmCombo);
			menu_form.add(mi_frmButton);
			//menu_form.add(mi_frmButtons);
			mi_frmButtons.add(mi_frmBtnSubmit);
			mi_frmButtons.add(mi_frmBtnReset);
			mi_frmButtons.add(mi_frmBtnImage);
			mi_frmButtons.add(mi_frmBtnSimple);
			menu_LangSelect.add(mi_langUrdu);
			menu_LangSelect.add(mi_langEnglish);
			menu_Language.add(menu_LangSelect);
			menu_Language.addSeparator();
			menu_Language.add(mi_ViewKeyboard);
			ButtonGroup rbgp=new ButtonGroup();
			rbgp.add(mi_UMasterLAF); 
			rbgp.add(mi_WindowLAF);
			//menu_LookAndFeel.add(rbgp);
			menu_LookAndFeel.add(mi_UMasterLAF);
			menu_LookAndFeel.add(mi_WindowLAF);
			//menu_Preferences.add(menu_LookAndFeel);
			menu_Preferences.add(mi_AutoSpellCheck);
			menu_help.add(mi_About);
			
			this.add(menu_file);
			this.add(menu_edit);
			this.add(menu_view);
			this.add(menu_insert);
			this.add(menu_format);
			this.add(menu_table);
			this.add(menu_form);
			this.add(menu_Language);
			this.add(menu_Preferences);
			this.add(menu_help);
		}
	}
	/**************************************************
	FORMAT TOOLBAR CLASS
	*/
	public class UMasterFormatToolBar extends JToolBar
	{
		UMasterUI m_MasterUI;
	//JMenuBar=new JMenuBar();
		
	
		ImageIcon tbIcnBold,tbIcnItalic,tbIcnUnderline,tbIcnLeftAlign,tbIcnCenterAlign,tbIcnRightAlign,tbIcnJustify,tbIcnFColor,tbIcnBgColor,tbIcnFont;
		JButton   tbBtnBold,tbBtnItalic,tbBtnUnderline,tbBtnLeftAlign,tbBtnCenterAlign,tbBtnRightAlign,tbBtnJustify,tbBtnFColor,tbBtnBgColor,tbBtnFont;
		ImageIcon tbIcnOl,tbIcnUl,tbIcnDIndent,tbIcnIndent,tbIcnStrike,tbIcnSubscript,tbIcnSuperscript;
		JButton tbBtnOl,tbBtnUl,tbBtnDIndent,tbBtnIndent,tbBtnStrike,tbBtnSubscript,tbBtnSuperscript;
		JComboBox tbCmbFonts,tbCmbFontSize;
	
		public UMasterFormatToolBar()
		{
		
		//******************* DEFINE ICONS 
		tbIcnBold=new ImageIcon(m_btn_path+"bold.gif");
		tbIcnItalic=new ImageIcon(m_btn_path+"Italic.gif");
		tbIcnUnderline=new ImageIcon(m_btn_path+"under.gif");
		tbIcnLeftAlign=new ImageIcon(m_btn_path+"left.gif");
		tbIcnRightAlign=new ImageIcon(m_btn_path+"right.gif");
		tbIcnCenterAlign=new ImageIcon(m_btn_path+"center.gif");
		tbIcnJustify=new ImageIcon(m_btn_path+"justify.gif");
		tbIcnFColor=new ImageIcon(m_btn_path+"forecolor.gif");
		tbIcnBgColor=new ImageIcon(m_btn_path+"backcolor.gif");
		tbIcnOl=new ImageIcon(m_btn_path+"ol.gif");
		tbIcnUl=new ImageIcon(m_btn_path+"ul.gif");
		tbIcnDIndent=new ImageIcon(m_btn_path+"deindent.gif");
		tbIcnIndent=new ImageIcon(m_btn_path+"inindent.gif");
		tbIcnStrike=new ImageIcon(m_btn_path+"strikethrough.gif");
		tbIcnSubscript=new ImageIcon(m_btn_path+"subscript.gif");
		tbIcnSuperscript=new ImageIcon(m_btn_path+"superscript.gif");
		tbIcnFont=new ImageIcon(m_btn_path+"font.gif");
		
		//****************** DEFINE BUTTONS
		tbBtnBold=new JButton(tbIcnBold);
		tbBtnBold.setToolTipText("Bold");
		tbBtnItalic=new JButton(tbIcnItalic);
		tbBtnItalic.setToolTipText("Italic");
		tbBtnUnderline=new JButton(tbIcnUnderline);
		tbBtnUnderline.setToolTipText("Underline");
		tbBtnLeftAlign=new JButton(tbIcnLeftAlign);
		tbBtnLeftAlign.setToolTipText("Left Align");
		tbBtnCenterAlign=new JButton(tbIcnCenterAlign);
		tbBtnCenterAlign.setToolTipText("Center Align");
		tbBtnRightAlign=new JButton(tbIcnRightAlign);
		tbBtnRightAlign.setToolTipText("Right Align");
		tbBtnJustify=new JButton(tbIcnJustify);
		tbBtnJustify.setToolTipText("Justify");
		tbBtnFColor=new JButton(tbIcnFColor);
		tbBtnFColor.setToolTipText("Foreground");
		tbBtnBgColor=new JButton(tbIcnBgColor);
		tbBtnBgColor.setToolTipText("Background");
		tbBtnOl=new JButton(tbIcnOl);
		tbBtnOl.setToolTipText("Ordered List");
		tbBtnUl=new JButton(tbIcnUl);
		tbBtnUl.setToolTipText("Unordered List");
		tbBtnDIndent=new JButton(tbIcnDIndent);
		tbBtnDIndent.setToolTipText("Decrease Indent");
		tbBtnIndent=new JButton(tbIcnIndent);
		tbBtnIndent.setToolTipText("Increase Indent");
		tbBtnStrike=new JButton(tbIcnStrike);
		tbBtnStrike.setToolTipText("Strike Through");
		tbBtnSubscript=new JButton(tbIcnSubscript);
		tbBtnSubscript.setToolTipText("Subscript");
		tbBtnSuperscript=new JButton(tbIcnSuperscript);
		tbBtnSuperscript.setToolTipText("Superscript");
		tbBtnFont=new JButton(tbIcnFont);
		tbBtnFont.setToolTipText("Font");
		
		//*******************************
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames=ge.getAvailableFontFamilyNames();
		tbCmbFonts=new JComboBox(fontNames);
		tbCmbFonts.setToolTipText("Font Style");
		//for(int i=0;i<fontNames.length;i++)
		//{
			//tbCmbFonts.setFont((Font)Font.decode(fontNames[0]));
		//}
		tbCmbFonts.setMaximumSize(new Dimension(150,30));
		tbCmbFonts.setEditable(true);
		tbCmbFontSize=new JComboBox(new String[] {"8","9","10","11","12","14","16","18","20","24","26","28","36","48","72"});
		tbCmbFontSize.setToolTipText("Size");
		tbCmbFontSize.setMaximumSize(new Dimension(70,30));
		tbCmbFontSize.setEditable(true);
		
		
		
		//****************** ADD ACTIONS
		tbBtnBold.addActionListener(
			new HTMLEditorKit.BoldAction()
			);
		tbBtnItalic.addActionListener(
			
			new HTMLEditorKit.ItalicAction()
			);
		tbBtnUnderline.addActionListener(
			
			new HTMLEditorKit.UnderlineAction()
			);
		tbBtnLeftAlign.addActionListener(
			new HTMLEditorKit.AlignmentAction("AlignLeft", StyleConstants.ALIGN_LEFT)
			);
		tbBtnCenterAlign.addActionListener(
			new HTMLEditorKit.AlignmentAction("AlignCenter", StyleConstants.ALIGN_CENTER)
			);
		tbBtnRightAlign.addActionListener(
			new HTMLEditorKit.AlignmentAction("AlignRight", StyleConstants.ALIGN_RIGHT)
			);
		tbBtnJustify.addActionListener(
			new HTMLEditorKit.AlignmentAction("AlignJustify", StyleConstants.ALIGN_JUSTIFIED)
			);
		tbBtnStrike.addActionListener(
			
			new StrikeThroughAction()
			);
		tbBtnSubscript.addActionListener(
			
			new SubscriptAction()
			);
		tbBtnSuperscript.addActionListener(
			
			new SuperscriptAction()
			);
		tbBtnUl.addActionListener(
				//new HTMLEditorKit.InsertHTMLTextAction("ULAction","<li> </li>",HTML.Tag.BODY,HTML.Tag.UL)
				//new HTMLEditorKit.InsertUnorderedList()
			/*new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					//MutableAttributeSet attr=new SimpleAttributeSet();
					//HTML.Tag bullet=HTML.Tag.LI;
					//attr.addAttribute(StyleConstants.NameAttribute,bullet);
					//StyledEditorKit.StyledTextAction.setParagraphAttributes(m_internalFrame_document.m_designDocument,attr,true);
					//m_internalFrame_document.m_designDocument.grabFocus();
					InsertHTMLTextAction("ULAction","<ul><li></li></ul>",HTML.Tag.BODY,HTML.Tag.UL);
				}
			}*/
			new ULAction()
			);
		/*tbBtnOl.addActionListener(
			new OLAction()
			//new HTMLEditorKit.InsertHTMLTextAction("OLAction","<ol><li> </li></ol>",HTML.Tag.BODY,HTML.Tag.OL)
			);*/
		tbBtnOl.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_OL();
				}
			}
			);
		tbBtnIndent.addActionListener(
			//public void  actionPerformed(ActionEvent ae)
				//{
					
				//}
				new InsertTagAction(HTML.Tag.BLOCKQUOTE)
			);
		tbBtnFont.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setFont();
				}
			}
			);

		tbCmbFonts.addActionListener(
			///*
			new ActionListener()
			{
				public void  actionPerformed(ActionEvent ae)
				{
					try{
					_setFontFamily(tbCmbFonts.getSelectedItem().toString());
					}
					catch(Exception ex)
					{
						System.out.println(ex);
					}
				}
			}
			//*/
			//	new FontFamilyAction(tbCmbFonts.getSelectedItem().toString(),m_internalFrame_document.m_designDocument)
			//	new FontFamilyAction(tbCmbFonts.getSelectedItem().toString())
			);
			
		
		tbCmbFontSize.addActionListener(
			new ActionListener()
			{
			public void  actionPerformed(ActionEvent ae)
				{
					_setFontSize( Integer.parseInt(tbCmbFontSize.getSelectedItem().toString()));
					
				}
			}
			);
		tbBtnFColor.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setForeColor();
				}
			}
			);
		tbBtnBgColor.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setBackColor();
				}
			}
			);

		
		//****************** ADD BUTTONS
		this.add(tbBtnBold);
		this.add(tbBtnItalic);
		this.add(tbBtnUnderline);
		this.addSeparator();
		this.add(tbBtnLeftAlign);
		this.add(tbBtnCenterAlign);
		this.add(tbBtnRightAlign);
		this.add(tbBtnJustify);
		this.addSeparator();
		//this.add(new JButton());
		this.add(tbCmbFonts);
		//this.add(new JButton());
		this.add(tbCmbFontSize);
		this.addSeparator();
		this.add(tbBtnFont);
		this.add(tbBtnFColor);
		this.add(tbBtnBgColor);
		this.addSeparator();
		this.add(tbBtnOl);
		this.add(tbBtnUl);
		//this.add(tbBtnDIndent);
		//this.add(tbBtnIndent);
		this.addSeparator();
		this.add(tbBtnStrike);
		this.add(tbBtnSubscript);
		this.add(tbBtnSuperscript);
		
		}
	
		//****************** DEFINE METHODS
		public void setMasterUI(UMasterUI _MasterUI)
		{
			m_MasterUI=_MasterUI;
		}
			
		public void tbBold()
		{
			//_bold();
			new StyledEditorKit.BoldAction();
		}
		

	}
	
	
	/**************************************************
	FILE TOOLBAR CLASS
	*/
	public class UMasterFileToolBar extends JToolBar
	{
	//JMenuBar=new JMenuBar();
	
	ImageIcon tbIcnNew,tbIcnOpen,tbIcnAddItm,tbIcnSave,tbIcnSaveAs,tbIcnCut,tbIcnCopy,tbIcnPaste,tbIcnUndo,tbIcnRedo;
	JButton   tbBtnNew,tbBtnOpen,tbBtnAddItm,tbBtnSave,tbBtnSaveAs,tbBtnCut,tbBtnCopy,tbBtnPaste,tbBtnUndo,tbBtnRedo;
	ImageIcon tbIcnSelectAll,tbIcnBrowser,tbIcnFullScreen,tbIcnFind,tbIcnFindNext;
	JButton tbBtnSelectAll,tbBtnBrowser,tbBtnFullScreen,tbBtnFind,tbBtnFindNext;
	public JTextField tbTfFind;
	int searchIndex=0;
	
	public UMasterFileToolBar()
	{
		
		//******************* DEFINE ICONS 
		tbIcnNew=new ImageIcon(m_btn_path+"newdoc.gif");
		tbIcnOpen=new ImageIcon(m_btn_path+"open.gif");
		tbIcnAddItm=new ImageIcon(m_btn_path+"additem.gif");
		tbIcnSave=new ImageIcon(m_btn_path+"save.gif");
		tbIcnSaveAs=new ImageIcon(m_btn_path+"saveas.gif");
		tbIcnCut=new ImageIcon(m_btn_path+"cut.gif");
		tbIcnCopy=new ImageIcon(m_btn_path+"copy.gif");
		tbIcnPaste=new ImageIcon(m_btn_path+"paste.gif");
		tbIcnUndo=new ImageIcon(m_btn_path+"undo.gif");
		tbIcnRedo=new ImageIcon(m_btn_path+"redo.gif");
		tbIcnSelectAll=new ImageIcon(m_btn_path+"selall.gif");
		tbIcnBrowser=new ImageIcon(m_btn_path+"browser.png");
		tbIcnFullScreen=new ImageIcon(m_btn_path+"fullscrn.gif");
		tbIcnFind=new ImageIcon(m_btn_path+"find.gif");
		tbIcnFindNext=new ImageIcon(m_btn_path+"findnext.gif");
		
		//******************* DEFINE BIUTTONS
		tbBtnNew=new JButton(tbIcnNew);
		tbBtnNew.setToolTipText("New Document");
		tbBtnOpen=new JButton(tbIcnOpen);
		tbBtnOpen.setToolTipText("Open");
		tbBtnAddItm=new JButton(tbIcnAddItm);
		tbBtnAddItm.setToolTipText("Add");
		tbBtnSave=new JButton(tbIcnSave);
		tbBtnSave.setToolTipText("Save");
		tbBtnSaveAs=new JButton(tbIcnSaveAs);
		tbBtnSaveAs.setToolTipText("Save As");
		tbBtnCut=new JButton(tbIcnCut);
		tbBtnCut.setToolTipText("Cut");
		tbBtnCopy=new JButton(tbIcnCopy);
		tbBtnCopy.setToolTipText("Copy");
		tbBtnPaste=new JButton(tbIcnPaste);
		tbBtnPaste.setToolTipText("Paste");
		tbBtnUndo=new JButton(tbIcnUndo);
		tbBtnUndo.setToolTipText("Undo");
		tbBtnRedo=new JButton(tbIcnRedo);
		tbBtnRedo.setToolTipText("Redo");
		tbBtnSelectAll=new JButton(tbIcnSelectAll);
		tbBtnSelectAll.setToolTipText("Select All");
		tbBtnBrowser=new JButton(tbIcnBrowser);
		tbBtnFullScreen=new JButton(tbIcnFullScreen);
		tbBtnFind=new JButton(tbIcnFind);
		tbBtnFind.setToolTipText("Find");
		tbBtnFindNext=new JButton(tbIcnFindNext);
		tbBtnFindNext.setToolTipText("Find Next");
		
		//******************* OTHER DIFINATIONS
		tbTfFind=new JTextField(10);
		tbTfFind.setMaximumSize(new Dimension(150,30));
		
		
		//******************* ADD ACTIONS
		tbBtnCut.addActionListener(new HTMLEditorKit.CutAction());
		tbBtnCopy.addActionListener(new HTMLEditorKit.CopyAction());
		tbBtnPaste.addActionListener(new HTMLEditorKit.PasteAction());
		
		tbBtnNew.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_newDocument();
				}
			}
			);
		tbBtnOpen.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_openDocument();
				}
			}
			);
		tbBtnSave.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					if(!_saveFile(false))
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not save the file","Error Saving File",JOptionPane.ERROR_MESSAGE);
				}
			}
			);
		tbBtnSaveAs.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					if(!_saveFile(true))
						JOptionPane.showMessageDialog(m_internalFrame_document.tpPreviewDocument,"Could not save the file","Error Saving File",JOptionPane.ERROR_MESSAGE);
				}
			}
			);
		tbBtnUndo.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_undo();
					_updateUndo();
				}
			}
			);
		tbBtnRedo.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_redo();
					_updateUndo();
				}
			}
			);
		tbTfFind.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					searchIndex=_find(tbTfFind.getText(),0);
				}
			}
			);
		tbBtnFindNext.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					searchIndex=_find(tbTfFind.getText(),searchIndex);
				}
			}
			);
		//******************* ADD BUTTONS
		this.add(tbBtnNew);
		//this.add(tbBtnAddItm);
		this.add(tbBtnOpen);
		this.add(tbBtnSave);
		this.add(tbBtnSaveAs);
		this.addSeparator();
		this.add(tbBtnCut);
		this.add(tbBtnCopy);
		this.add(tbBtnPaste);
		this.addSeparator();
		this.add(tbBtnUndo);
		this.add(tbBtnRedo);
		this.addSeparator();
		this.add(tbBtnFind);
		this.add(tbTfFind);
		this.add(tbBtnFindNext);
		this.addSeparator();
		//this.add(tbBtnSelectAll);
		//this.add(tbBtnBrowser);
		//this.add(tbBtnFullScreen);
		
		
	}
	
	//******************* DEFINE METHODS
	/*protected void updateUndo() 
	{
		_updateUndo();
		if(_canUndo()) 
		{
			tbBtnUndo.setEnabled(true);
			mi_Undo.setEnabled(true);
			//m_undoAction.putValue(Action.NAME,m_undo.getUndoPresentationName());
		}
		
		else 
		{
			tbBtnUndo.setEnabled(false);
			mi_Undo.setEnabled(false);
			//m_undoAction.putValue(Action.NAME, "Undo");
		}
		if(_canRedo()) 
		{
			tbBtnRedo.setEnabled(true);
			mi_Redo.setEnabled(true);
			//m_redoAction.putValue(Action.NAME,m_undo.getRedoPresentationName());
		}
		else 
		{
			tbBtnRedo.setEnabled(false);
			mi_Redo.setEnabled(false);
			//m_redoAction.putValue(Action.NAME, "Redo");
		}
	}*/
		
	}
	
	/**************************************************
	HTML TOOLBAR CLASS
	*/
	public class UMasterHTMLToolBar extends JToolBar
	{
	//JMenuBar=new JMenuBar();
	UMasterUI m_MasterUI;
	
	ImageIcon tbIcnHyperlink,tbIcnImage,tbIcnP,tbIcnHR,tbIcnBR;
	JButton   tbBtnHyperlink,tbBtnImage,tbBtnP,tbBtnHR,tbBtnBR;
	ImageIcon tbIcnInTable,tbIcnInRow,tbIcnInCol,tbIcnInCell,tbIcnDelRow,tbIcnDelCol,tbIcnDelCell,tbIcnSelTable,tbIcnSelRow,tbIcnSelCol,tbIcnSelCell,tbIcnSplitCells,tbIcnMergeCells,tbIcnTabProperties,tbIcnCellProperties;
	JButton   tbBtnInTable,tbBtnInRow,tbBtnInCol,tbBtnInCell,tbBtnDelRow,tbBtnDelCol,tbBtnDelCell,tbBtnSelTable,tbBtnSelRow,tbBtnSelCol,tbBtnSelCell,tbBtnSplitCells,tbBtnMergeCells,tbbtnTabProperties,tbBtnCellProperties;
	ImageIcon tbIcnKeyboard,tbIcnBrowser,tbIcnSpell;
	JButton	  tbBtnKeyboard,tbBtnBrowser,tbBtnSpell;
	JComboBox tbCmbStyles,tbCmbHeadings;
	ImageIcon 		tbIcnLang;
	JToggleButton 	tbBtnLang;

	
	public UMasterHTMLToolBar()
	{
		
		
		
		
		//******************* DEFINE ICONS 
		tbIcnHyperlink=new ImageIcon(m_btn_path+"insert_hyperlink.gif");
		tbIcnImage=new ImageIcon(m_btn_path+"insert_picture.gif");
		tbIcnHR=new ImageIcon(m_btn_path+"hr.gif");
		tbIcnBR=new ImageIcon(m_btn_path+"br.gif");
		tbIcnInTable=new ImageIcon(m_btn_path+"insert_table.gif");
		tbIcnInRow=new ImageIcon(m_btn_path+"insrow.gif");
		tbIcnInCol=new ImageIcon(m_btn_path+"inscol.gif");
		tbIcnInCell=new ImageIcon(m_btn_path+"inscell.gif");
		tbIcnDelRow=new ImageIcon(m_btn_path+"delrow.gif");
		tbIcnDelCol=new ImageIcon(m_btn_path+"delcol.gif");
		tbIcnDelCell=new ImageIcon(m_btn_path+"delcell.gif");
		tbIcnSelTable=new ImageIcon(m_btn_path+"");
		tbIcnSelRow=new ImageIcon(m_btn_path+"");
		tbIcnSelCol=new ImageIcon(m_btn_path+"");
		tbIcnSelCell=new ImageIcon(m_btn_path+"");
		tbIcnSplitCells=new ImageIcon(m_btn_path+"");
		tbIcnMergeCells=new ImageIcon(m_btn_path+"");
		tbIcnTabProperties=new ImageIcon(m_btn_path+"");
		tbIcnCellProperties=new ImageIcon(m_btn_path+"");
		tbIcnKeyboard=new ImageIcon(m_btn_path+"keyboard.gif");
		tbIcnLang=new ImageIcon(m_btn_path+"urdu.gif");
		tbIcnBrowser=new ImageIcon(m_btn_path+"browser.gif");
		tbIcnSpell=new ImageIcon(m_btn_path+"spellcheck.gif");

		//******************* DEFINE BIUTTONS
		tbBtnHyperlink=new JButton(tbIcnHyperlink);
		tbBtnHyperlink.setToolTipText("Insert Link");
		tbBtnImage=new JButton(tbIcnImage);
		tbBtnImage.setToolTipText("Insert Image");
		tbBtnHR=new JButton(tbIcnHR);
		tbBtnHR.setToolTipText("Insert Rule");
		tbBtnBR=new JButton(tbIcnBR);
		tbBtnBR.setToolTipText("Insert Break");
		tbBtnInTable=new JButton(tbIcnInTable);
		tbBtnInTable.setToolTipText("Insert Table");
		tbBtnInRow=new JButton(tbIcnInRow);
		tbBtnInRow.setToolTipText("Insert Row");
		tbBtnInCol=new JButton(tbIcnInCol);
		tbBtnInCol.setToolTipText("Insert Column");
		tbBtnInCell=new JButton(tbIcnInCell);
		tbBtnInCell.setToolTipText("Insert Cell");
		tbBtnDelRow=new JButton(tbIcnDelRow);
		tbBtnDelRow.setToolTipText("Delete Row");
		tbBtnDelCol=new JButton(tbIcnDelCol);
		tbBtnDelCol.setToolTipText("Delete Column");
		tbBtnDelCell=new JButton(tbIcnDelCell);
		tbBtnDelCell.setToolTipText("Delete Cell");
		//tbBtnLang=new JToggleButton("\u0627\u0631\u062F\u0648",tbIcnLang);
		tbBtnLang=new JToggleButton(tbIcnLang,false);
		tbBtnLang.setFont(new Font("Ariel",1,14));
		tbBtnLang.setToolTipText("Language");
		tbBtnKeyboard=new JButton(tbIcnKeyboard);
		tbBtnKeyboard.setToolTipText("View Keyboard (Ctrl+K)");
		tbBtnBrowser=new JButton(tbIcnBrowser);
		tbBtnBrowser.setToolTipText("View in Browser");
		tbBtnSpell=new JButton(tbIcnSpell);
		tbBtnSpell.setToolTipText("Spell Check (F7)");
		//******************* OTHER DIFINATIONS

		tbCmbHeadings=new JComboBox();
		tbCmbHeadings.setToolTipText("Heading Style");
		tbCmbHeadings.addItem("Headings");
		tbCmbHeadings.addItem(HTML.Tag.H1);
		tbCmbHeadings.addItem(HTML.Tag.H2);
		tbCmbHeadings.addItem(HTML.Tag.H3);
		tbCmbHeadings.addItem(HTML.Tag.H4);
		tbCmbHeadings.addItem(HTML.Tag.H5);
		tbCmbHeadings.addItem(HTML.Tag.H6);
		tbCmbHeadings.setMaximumSize(new Dimension(100,30));

		tbCmbStyles=new JComboBox();
		tbCmbStyles.setToolTipText("HTML Styles");
		tbCmbStyles.addItem("Styles");
		tbCmbStyles.addItem(HTML.Tag.ADDRESS);
		tbCmbStyles.addItem(HTML.Tag.BIG);
		tbCmbStyles.addItem(HTML.Tag.CAPTION);
		tbCmbStyles.addItem(HTML.Tag.CITE);
		tbCmbStyles.addItem(HTML.Tag.CODE);
		tbCmbStyles.addItem(HTML.Tag.EM);
		tbCmbStyles.addItem(HTML.Tag.PRE);
		tbCmbStyles.addItem(HTML.Tag.STRONG);
		
		tbCmbStyles.setMaximumSize(new Dimension(70,30));
		
		//******************* ADD ACTIONS
		///*
		tbBtnHyperlink.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_insertHyperlink();
					}
					catch(Exception ex)
					{
					}
				}
			}
			);
		tbCmbHeadings.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(tbCmbHeadings.getSelectedIndex()==0)
						return;
					_setHeading((HTML.Tag)tbCmbHeadings.getSelectedItem());
				}
			}
			);
		
		tbCmbStyles.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(tbCmbStyles.getSelectedIndex()==0)
						return;
					_setHeading((HTML.Tag)tbCmbStyles.getSelectedItem());
				}
			}
			);
			//*/
		//tbCmbHeadings.addActionListener(new HTMLEditorKit.InsertHTMLTextAction("Heading 1", "<hl>[H1]</hl>",HTML.Tag.BODY, HTML.Tag.H1));
	
		tbBtnImage.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						_insertImage2();
					}
					catch(Exception ex)
					{
					}
				}
			}
			);
			/*
		tbBtnP.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_insertPara();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);*/
		tbBtnBR.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_insertBreak();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
		tbBtnHR.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_insertHR();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
		tbBtnInTable.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTable();
				}
			}
			);
		tbBtnInRow.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableRow();
				}
			}
			);
		tbBtnInCol.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableColumn();
				}
			}
			);
		tbBtnInCell.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_insertTableCell();
				}
			}
			);
		tbBtnDelRow.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableRow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
		tbBtnDelCol.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableCol();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
		tbBtnDelCell.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						_deleteTableCell();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			);
		tbBtnLang.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(tbBtnLang.isSelected())
						_setKeymap("Urdu");
					else
						_setKeymap("English");
				}
			}
			);
		tbBtnKeyboard.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_showKeyboard();
				}
			}
			);
		tbBtnSpell.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_showSpellCheckerDialog();
				}
			}
			);
		tbBtnBrowser.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					_showInBrowser();
				}
			}
			);
		
		//******************* ADD BUTTONS
		this.add(tbBtnHyperlink);
		this.add(tbBtnImage);
		this.addSeparator();
		this.add(tbCmbHeadings);
		this.add(tbCmbStyles);
		this.add(tbBtnHR);
		this.add(tbBtnBR);
		this.addSeparator();
		this.add(tbBtnInTable);
		this.add(tbBtnInRow);
		this.add(tbBtnInCol);
		this.add(tbBtnInCell);
		this.add(tbBtnDelRow);
		this.add(tbBtnDelCol);
		this.add(tbBtnDelCell);
		this.addSeparator();
		this.add(tbBtnLang);	
		this.add(tbBtnKeyboard);
		this.add(tbBtnSpell);	
		this.add(tbBtnBrowser);	
		}
	
	//******************* DEFINE METHODS
		public void setMasterUI(UMasterUI _MasterUI)
		{
			m_MasterUI=_MasterUI;
		}
	}

	/**************************************************
	PROPERTIES PANEL CLASS
	*/
	public class UMasterPropertiesPane extends JTabbedPane
	{
		
		JPanel jp_docProperties,jp_format,jp_tableProperties,jp_form;	
		
		UMasterFormatToolBar tb_format;
		
		
		UMasterPropertiesPane()
		{
			jp_docProperties=new JPanel();
			jp_format=new JPanel(new BorderLayout());
			jp_tableProperties=new JPanel();
			jp_form=new JPanel();
			
			tb_format=new UMasterFormatToolBar();
			jp_format.add(tb_format,BorderLayout.CENTER);
			
			
			this.add("Document Properties",jp_docProperties);
			this.add("Format",jp_format);
			this.add("Table",jp_tableProperties);
			this.add("Form",jp_form);
		}
	}
	
	/**************************************************
	POPUP MENU CLASS
	*/
	
	public class UMasterPopMenu extends JPopupMenu
	{
		JMenuItem pmi_Cut,pmi_Copy,pmi_Paste,pmi_Find,pmi_Replace,pmi_SelectAll,pmi_Font,pmi_Hyperlink,pmi_Image;
		
		UMasterPopMenu()
		{
			pmi_Cut=new JMenuItem("Cut",new ImageIcon(m_btn_path+"cut.gif"));
			pmi_Cut.setAccelerator(KeyStroke.getKeyStroke('X',Event.CTRL_MASK));
			pmi_Copy=new JMenuItem("Copy",new ImageIcon(m_btn_path+"copy.gif"));
			pmi_Copy.setAccelerator(KeyStroke.getKeyStroke('C',Event.CTRL_MASK));
			pmi_Paste=new JMenuItem("Paste",new ImageIcon(m_btn_path+"paste.gif"));
			pmi_Paste.setAccelerator(KeyStroke.getKeyStroke('V',Event.CTRL_MASK));
			pmi_Find=new JMenuItem("Find",new ImageIcon(m_btn_path+"find.gif"));
			pmi_Find.setAccelerator(KeyStroke.getKeyStroke('F',Event.CTRL_MASK));
			pmi_Replace=new JMenuItem("Replace",new ImageIcon(m_btn_path+"findnext.gif"));
			pmi_Replace.setAccelerator(KeyStroke.getKeyStroke('H',Event.CTRL_MASK));
			pmi_SelectAll=new JMenuItem("Select All",new ImageIcon(m_btn_path+"selall.gif"));
			pmi_SelectAll.setAccelerator(KeyStroke.getKeyStroke('A',Event.CTRL_MASK));
			pmi_Font=new JMenuItem("Font",new ImageIcon(m_btn_path+"font.gif"));
			pmi_Hyperlink=new JMenuItem("Hyperlink",new ImageIcon(m_btn_path+"insert_hyperlink.gif"));
			pmi_Image=new JMenuItem("Insert Image",new ImageIcon(m_btn_path+"insert_picture.gif"));
			
			pmi_Cut.addActionListener(new HTMLEditorKit.CutAction());
			pmi_Copy.addActionListener(new HTMLEditorKit.CopyAction());
			pmi_Paste.addActionListener(new HTMLEditorKit.PasteAction());
			pmi_SelectAll.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_internalFrame_document.tpDesignDocument.selectAll();
					}
				}
				);
			pmi_Font.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_setFont();
				}
			}
			);
			pmi_Find.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_find();
				}
			}
			);
			pmi_Replace.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					_replace();
				}
			}
			);
			pmi_Hyperlink.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						_insertHyperlink();
					}
					catch(Exception e){}
				}
			}
			);
			pmi_Image.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						_insertImage2();
					}
					catch(Exception e){}
				}
			}
			);
			
			//this.add(_getCheckerMenu());
			this.add(pmi_Cut);
			this.add(pmi_Copy);
			this.add(pmi_Paste);
			this.addSeparator();
			this.add(pmi_Find);
			this.add(pmi_Replace);
			this.add(pmi_SelectAll);
			this.add(pmi_Font);
			this.addSeparator();
			this.add(pmi_Hyperlink);
			this.add(pmi_Image);
		}
	}
	
	
	/**************************************************
	STATUS MENU CLASS
	*/
	
	
}