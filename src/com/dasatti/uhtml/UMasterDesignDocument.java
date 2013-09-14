package com.dasatti.uhtml;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
//import javax.swing.text.rtf.*;
import javax.swing.text.html.*;

public class UMasterDesignDocument extends UMasterAbstractDocument
{
	JTextPane m_designDocument;
        ExtendedHTMLEditorKit m_eEditorKit;
	ExtendedHTMLDocument m_designHTMLDocument;
	DefaultStyledDocument m_styledDoc;
	MutableAttributeSet attr;
	JScrollPane m_sPane;
	
	UMasterDesignDocument()
	{
		setLayout(new BorderLayout());
                m_eEditorKit = new ExtendedHTMLEditorKit();
		m_designHTMLDocument = (ExtendedHTMLDocument)(m_eEditorKit.createDefaultDocument());
		//m_designHTMLDocument=new HTMLDocument();
		m_styledDoc=new DefaultStyledDocument();
		m_designDocument=new JTextPane(m_styledDoc);
		m_designDocument.setEditorKit(new HTMLEditorKit());
		m_designDocument.setContentType("text/html");
	//	_setPage("test.html");
		/*
		FocusListener fl=new FocusListener()
		{
			public void focusGained(FocusEvent fe)
			{
				
				_updateLocalDoc();
			}
			public void focusLost(FocusEvent fe)
			{
				
				_updateCurrentDoc();
			}
		};
		
		m_designDocument.addFocusListener(fl);
		*/
		
		
		m_sPane=new JScrollPane(m_designDocument);
		add(m_sPane);
	}
	
	/***************************
	PUBLIC METHODS
	***************************/
	public void _setPage(String _path)
	{
		java.net.URL helpURL = UMasterDesignDocument.class.getResource(_path);
		try
		{
			m_designDocument.setPage(helpURL);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(m_designDocument,"File could not be opened", "File Open Error",JOptionPane.ERROR_MESSAGE);

		}
	}
	
	public ExtendedHTMLEditorKit _getExtendedHTMLEditorKit()
        {
            return m_eEditorKit;
        }
	
	public void _updateCurrentDoc()
	{
		
	}
	
	public void _updateLocalDoc()
	{
		
	}
	
	public void _bold()
	{
	
	}
	public void _cut()
	{
		System.out.println("Cut");
	}
	
	
	
}
